package got.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.network.base.PacketDispatcher;
import got.common.network.serverToClient.PacketSendBounceCooldown;
import got.common.network.serverToClient.PacketSendStamina;
import got.common.registers.EffectRegister;
import got.rome.ExtendedPlayer;
import javafx.scene.effect.Effect;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovementInput;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class StaminaServerHandler {

    public static final StaminaServerHandler INSTANCE = new StaminaServerHandler();

    public static final int MAX_STAMINA = 300;
    private static final int DRAIN_RATE_RUNNING = 1;

    private static final int BOUNCE_RANGE = 1; // New field for bounce range
    private static final int DRAIN_RATE_JUMPING = 2;
    private static final int REGAIN_RATE = 10;
    private static final int STANDING_STILL_COOLDOWN = 30;

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

        if (isMoving && !isRunning) {
            drainStaminaByPercent(0.35, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN);
        }

        if (isRunning) {
            drainStaminaByPercent(0.5, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN);
        }

        if (isJumping) {
            drainStaminaByPercent(0.7, player);
            extendedPlayer.setStandingStillCooldown(STANDING_STILL_COOLDOWN);
        }

        if (extendedPlayer.getStandingStillCooldown() > 0 && !isMoving && !isJumping) {
            extendedPlayer.setStandingStillCooldown(extendedPlayer.getStandingStillCooldown() - 1);
        }

        if (player.isPotionActive(EffectRegister.rest)) { // Resting potion effect stamina regen, stacks with normal regen
            regainStamina(REGAIN_RATE * 2, player);
        }

        if (!isMoving && player.onGround && !player.isSprinting() && extendedPlayer.getStandingStillCooldown() == 0) {
            regainStamina(REGAIN_RATE, player);
        }

        // Decrease bounce cooldown
        if (extendedPlayer.getBounceCooldown() > 0) {
            extendedPlayer.setBounceCooldown(extendedPlayer.getBounceCooldown() - 1);
            PacketDispatcher.sendTo(new PacketSendBounceCooldown(extendedPlayer.getBounceCooldown()), (EntityPlayerMP) player);
        }

        if (extendedPlayer.getStamina() == 0) {
            if (player.isPotionActive(EffectRegister.secondBreath)) {
                regainStamina((int) (MAX_STAMINA * 0.1), player);
                player.removePotionEffect(EffectRegister.secondBreath.id);
            } else {
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 0, true));
            }
        } else {
            player.removePotionEffect(Potion.moveSlowdown.id);
        }

        extendedPlayer.setPreviousPosX(currentPosX);
        extendedPlayer.setPreviousPosZ(currentPosZ);

    }

    public void handleBounceRequest(EntityPlayer player, String direction) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);

        if (extendedPlayer.getBounceCooldown() > 0) {
            return;
        }

        if (direction.equals("left") || direction.equals("right") || direction.equals("backward")) {
            executeBounce(player, direction);
        }
    }

    private void executeBounce(EntityPlayer player, String direction) {
        double bounceRange = BOUNCE_RANGE; // Example bounce range
        double motionX = 0;
        double motionZ = 0;

        switch (direction) {
            case "backward":
                motionX = Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = -Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
            case "left":
                motionX = Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
            case "right":
                motionX = -Math.cos(Math.toRadians(player.rotationYaw)) * bounceRange;
                motionZ = -Math.sin(Math.toRadians(player.rotationYaw)) * bounceRange;
                break;
        }

        player.motionX += motionX;
        player.motionZ += motionZ;
        player.velocityChanged = true; // Ensure the server updates the player's velocity
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        drainStaminaByPercent(15, player);
        extendedPlayer.setBounceCooldown(20*3); // Set cooldown for bounce
        PacketDispatcher.sendTo(new PacketSendBounceCooldown(extendedPlayer.getBounceCooldown()), (EntityPlayerMP) player);
    }

    public static void drainStamina(int amount, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        int dexterityLevel = player.getActivePotionEffect(EffectRegister.dexterity) != null ? player.getActivePotionEffect(EffectRegister.dexterity).getAmplifier() + 1 : 0;
        double reductionFactor = 1.0 - (0.1 * Math.min(dexterityLevel, 3));
        int adjustedAmount = (int) (amount * reductionFactor);
        extendedPlayer.setStamina(Math.max(0, extendedPlayer.getStamina() - adjustedAmount));
        PacketDispatcher.sendTo(new PacketSendStamina(extendedPlayer.getStamina()), (EntityPlayerMP) player);
    }

    public static void drainStaminaByPercent(double percent, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        int dexterityLevel = player.getActivePotionEffect(EffectRegister.dexterity) != null ? player.getActivePotionEffect(EffectRegister.dexterity).getAmplifier() + 1 : 0;
        double reductionFactor = 1.0 - (0.1 * Math.min(dexterityLevel, 3));
        int amountToDrain = (int) (MAX_STAMINA * (percent / 100.0) * reductionFactor);
        extendedPlayer.setStamina(Math.max(0, extendedPlayer.getStamina() - amountToDrain));
        PacketDispatcher.sendTo(new PacketSendStamina(extendedPlayer.getStamina()), (EntityPlayerMP) player);
    }

    public static void regainStamina(int amount, EntityPlayer player) {
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        extendedPlayer.setStamina(Math.min(MAX_STAMINA, extendedPlayer.getStamina() + amount));
        PacketDispatcher.sendTo(new PacketSendStamina(extendedPlayer.getStamina()), (EntityPlayerMP) player);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if(player.worldObj.isRemote) {
                return;
            }
            DamageSource source = event.source;

            if (source == DamageSource.fall) {
                drainStaminaByPercent(1.0, player);
            } else if (source.isProjectile()) {
                drainStaminaByPercent(1.33, player);
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        EntityPlayer player = event.entityPlayer;
        if(player.worldObj.isRemote) {
            return;
        }
        drainStaminaByPercent(4.0, player);
    }

}