package got.common.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.common.network.base.PacketDispatcher;
import got.common.network.serverToClient.PacketSendStamina;
import got.common.systems.GOTCoreBlockingSystem;
import got.rome.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BlockServerHandler {

    public static final BlockServerHandler INSTANCE = new BlockServerHandler();

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.entity instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.entity;
        if (isBlocking(player)) {
            float[] blockAngles = GOTCoreBlockingSystem.getBlockAngles(player.getHeldItem().getItem().getClass());
            if (isDamageBlocked(player, event.source, blockAngles)) {
                event.setCanceled(true);
            } else {
                event.ammount += (float) (event.ammount * 0.3); // 30% more damage to make the 20% total reduction of damage(50% vanilla reduction + 30% more damage)
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