package got.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.common.systems.GOTCoreBlockingSystem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.lwjgl.Sys;

public class BlockServerHandler {

    public static final BlockServerHandler INSTANCE = new BlockServerHandler();

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {

        if (!(event.entity instanceof EntityPlayer)) {
            if(event.source.getEntity() instanceof EntityPlayer) {
                EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
                if (attacker.getHeldItem() != null && attacker.getHeldItem().getItem() instanceof ItemSword) {
                    StaminaServerHandler.drainStaminaByPercent(GOTCoreBlockingSystem.getBlockData(attacker.getHeldItem().getItem().getClass(), attacker).getStaminaMissPercent(), attacker);
                }
            }
        }

        if (!(event.entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entity;
        if (isBlocking(player)) {
            float[] blockAngles = {
                    GOTCoreBlockingSystem.getBlockData(player.getHeldItem().getItem().getClass(), player).getLeftBlockAngle(),
                    GOTCoreBlockingSystem.getBlockData(player.getHeldItem().getItem().getClass(), player).getRightBlockAngle()
            };
            if(event.source.getEntity() instanceof EntityPlayer) {
                EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
                StaminaServerHandler.drainStaminaByPercent(GOTCoreBlockingSystem.getBlockData(attacker.getHeldItem().getItem().getClass(), player).getStaminaMissPercent(), attacker);
            }
            if (isDamageBlocked(player, event.source, blockAngles)) {
                StaminaServerHandler.drainStaminaByPercent(5, player);
                player.worldObj.playSoundAtEntity(player, "got:combat_block", 1, 1);
                event.setCanceled(true);
            } else {
                StaminaServerHandler.drainStaminaByPercent(6, player);
                event.ammount += (float) (event.ammount * 0.15); // 15% more damage to make the 20% total reduction of damage(50% vanilla reduction + 30% more damage)
            }
        } else {
            if(event.source.getEntity() instanceof EntityPlayer) {
                EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
                StaminaServerHandler.drainStaminaByPercent(GOTCoreBlockingSystem.getBlockData(attacker.getHeldItem().getItem().getClass(), player).getStaminaHitPercent(), attacker);
            }
        }
    }

    public boolean isBlocking(EntityPlayer player) {
        return player.isUsingItem() && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemSword;
    }

    private boolean isDamageBlocked(EntityPlayer player, DamageSource source, float[] blockAngles) {
        // Calculate the player's facing direction
        float playerYaw = player.rotationYaw;

        // Check if the source entity is null
        if (source.getEntity() == null) {
            return false;
        }

        // Calculate the direction of the incoming attack
        double deltaX = source.getEntity().posX - player.posX;
        double deltaZ = source.getEntity().posZ - player.posZ;
        float attackYaw = (float) (Math.atan2(deltaZ, deltaX) * (180 / Math.PI)) - 90;

        // Compute the angle between the player's facing direction and the attack direction
        float angleDifference = Math.abs(playerYaw - attackYaw) % 360;
        if (angleDifference > 180) {
            angleDifference = 360 - angleDifference;
        }

        // Check if this angle is within the block angles
        return (angleDifference <= blockAngles[1] / 2) || (angleDifference >= 360 - blockAngles[0] / 2);
    }
}