package got.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.common.network.base.PacketDispatcher;
import got.common.network.serverToClient.PacketSendStamina;
import got.rome.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class StaminaServerHandler {

    public static final StaminaServerHandler INSTANCE = new StaminaServerHandler();

    public static final int MAX_STAMINA = 300;
    private static final int DRAIN_RATE_RUNNING = 1; // Stamina drain rate while running
    private static final int DRAIN_RATE_JUMPING = 2; // Stamina drain rate per jump
    private static final int REGAIN_RATE = 10; // Stamina regain rate while standing still
    private static final int STANDING_STILL_COOLDOWN = 100; // Ticks to wait before starting to regain stamina when standing still

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
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN); // Reset the cooldown to regain stamina when running
        }

        if (isJumping) {
            drainStamina(DRAIN_RATE_JUMPING, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN); // Reset the cooldown to regain stamina when jumping
        }

        if (extendedPlayer.getStandingStillCooldown() > 0 && !isMoving && !isJumping) {
            extendedPlayer.setStandingStillCooldown(extendedPlayer.getStandingStillCooldown() - 1);
        }

        if (!isMoving && player.onGround && !player.isSprinting() && extendedPlayer.getStandingStillCooldown() == 0) {
            regainStamina(REGAIN_RATE, player);
        }

        // Apply moveSlowdown effect if stamina is 0
        if (extendedPlayer.getStamina() == 0) {
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0, true));
        } else {
            player.removePotionEffect(Potion.moveSlowdown.id);
        }

        extendedPlayer.setPreviousPosX(currentPosX);
        extendedPlayer.setPreviousPosZ(currentPosZ);
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