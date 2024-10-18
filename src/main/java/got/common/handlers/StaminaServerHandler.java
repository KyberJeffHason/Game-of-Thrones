package got.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.common.network.base.PacketDispatcher;
import got.common.network.serverToClient.PacketSendStamina;
import got.rome.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;

public class StaminaServerHandler {

    public static final StaminaServerHandler INSTANCE = new StaminaServerHandler();

    public static final int MAX_STAMINA = 300;
    private static final int DRAIN_RATE_RUNNING = 1;

    private static final int BOUNCE_RANGE = 5; // New field for bounce range
    private static final int DRAIN_RATE_JUMPING = 2;
    private static final int REGAIN_RATE = 10;
    private static final int STANDING_STILL_COOLDOWN = 100;
    private static final int DOUBLE_TAP_INTERVAL = 10; // Ticks interval for double-tap detection

    private int lastTapTick = 0;
    private String lastTapDirection = "";

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        EntityPlayer player = event.player;
        if (player.worldObj.isRemote) {
            return;
        }

        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        boolean isRunning = player.isSprinting();
        boolean isJumping = player.motionY > 0 && !player.onGround;

        double currentPosX = player.posX;
        double currentPosZ = player.posZ;
        double previousPosX = extendedPlayer.getPreviousPosX();
        double previousPosZ = extendedPlayer.getPreviousPosZ();

        boolean isMoving = currentPosX != previousPosX || currentPosZ != previousPosZ;

        if (isRunning) {
            drainStamina(DRAIN_RATE_RUNNING, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN);
        }

        if (isJumping) {
            drainStamina(DRAIN_RATE_JUMPING, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN);
        }

        if (extendedPlayer.getStandingStillCooldown() > 0 && !isMoving && !isJumping) {
            extendedPlayer.setStandingStillCooldown(extendedPlayer.getStandingStillCooldown() - 1);
        }

        if (!isMoving && player.onGround && !player.isSprinting() && extendedPlayer.getStandingStillCooldown() == 0) {
            regainStamina(REGAIN_RATE, player);
        }

        if (extendedPlayer.getStamina() == 0) {
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0, true));
        } else {
            player.removePotionEffect(Potion.moveSlowdown.id);
        }

        extendedPlayer.setPreviousPosX(currentPosX);
        extendedPlayer.setPreviousPosZ(currentPosZ);

        handleBounce(player, extendedPlayer);
    }

    private void handleBounce(EntityPlayer player, ExtendedPlayer extendedPlayer) {
        if (extendedPlayer.getBounceCooldown() > 0) {
            extendedPlayer.setBounceCooldown(extendedPlayer.getBounceCooldown() - 1);
            return;
        }

        String currentDirection = getMovementDirection(player);
        int currentTick = player.ticksExisted;

        if (!currentDirection.isEmpty() && currentDirection.equals(lastTapDirection) && (currentTick - lastTapTick) <= DOUBLE_TAP_INTERVAL) {
            executeBounce(player, currentDirection);
            extendedPlayer.setBounceCooldown(10); // Set cooldown for bounce
        }

        if (!currentDirection.isEmpty() && !currentDirection.equals(lastTapDirection)) {
            lastTapDirection = currentDirection;
            lastTapTick = currentTick;
        }
    }

    private String getMovementDirection(EntityPlayer player) {
        MovementInput input = Minecraft.getMinecraft().thePlayer.movementInput;
        if (input.moveForward > 0) return "forward";
        if (input.moveForward < 0) return "backward";
        if (input.moveStrafe > 0) return "right";
        if (input.moveStrafe < 0) return "left";
        return "";
    }

    private void executeBounce(EntityPlayer player, String direction) {
        double bounceRange = BOUNCE_RANGE;
        double motionX = 0;
        double motionZ = 0;

        switch (direction) {
            case "forward":
                motionX = -Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
            case "backward":
                motionX = Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = -Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
            case "right":
                motionX = Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
            case "left":
                motionX = -Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = -Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
        }

        player.motionX += motionX;
        player.motionZ += motionZ;
        player.velocityChanged = true; // Ensure the server updates the player's velocity
    }

    private void drainStamina(int amount, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        extendedPlayer.setStamina(Math.max(0, extendedPlayer.getStamina() - amount));
        PacketDispatcher.sendTo(new PacketSendStamina(extendedPlayer.getStamina()), (EntityPlayerMP) player);
    }

    private void regainStamina(int amount, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        extendedPlayer.setStamina(Math.min(MAX_STAMINA, extendedPlayer.getStamina() + amount));
        PacketDispatcher.sendTo(new PacketSendStamina(extendedPlayer.getStamina()), (EntityPlayerMP) player);
    }
}