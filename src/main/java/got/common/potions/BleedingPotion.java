package got.common.potions;

import got.common.network.GOTPacketHandler;
import got.common.network.LOTRPacketParticleFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BleedingPotion extends CustomPotion {

    private Map<EntityLivingBase, Integer> movementCooldowns = new HashMap<>();

    private static final int COOLDOWN_TICKS = 10;

    private static final float DAMAGE_AMOUNT = 1.0F;

    public BleedingPotion(int id, boolean isBad, int fluidColor, ResourceLocation tex, String namePot) {
        super(id, isBad, fluidColor, tex, namePot);
    }

    public boolean isJumping(EntityLivingBase entity) {
        return entity.motionY > 0;
    }

    private Map<EntityLivingBase, Double[]> lastPositions = new HashMap<>();

    public boolean isMoving(EntityLivingBase entity) {
        // Retrieve the last known position of the entity
        Double[] lastPos = lastPositions.get(entity);

        // If no last position is recorded, initialize it and assume no movement
        if (lastPos == null) {
            lastPositions.put(entity, new Double[]{entity.posX, entity.posY, entity.posZ});
            return false;
        }

        // Calculate the difference in position between the current and last known positions
        double deltaX = entity.posX - lastPos[0];
        double deltaY = entity.posY - lastPos[1];
        double deltaZ = entity.posZ - lastPos[2];

        // Update the last position to the current position
        lastPositions.put(entity, new Double[]{entity.posX, entity.posY, entity.posZ});

        // Define thresholds for significant movement
        final double horizontalThreshold = 0.05D; // Increased threshold for X and Z axes
        final double verticalThreshold = 0.05D;   // Increased threshold for Y axis

        // Check if the entity has moved significantly in any direction
        boolean hasMoved = Math.abs(deltaX) > horizontalThreshold || Math.abs(deltaZ) > horizontalThreshold || Math.abs(deltaY) > verticalThreshold;

        return hasMoved;
    }


    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (isMoving(entity) || isJumping(entity)) {
            System.out.println("cocks");
        	//if(entity.getRNG().nextFloat() < 0.1f) {
        		entity.attackEntityFrom(DamageSource.generic, DAMAGE_AMOUNT * (amplifier + 1));
        	//}

            if (!entity.worldObj.isRemote) {
                double offset = 0.2D;
                double randOffsetX = entity.worldObj.rand.nextDouble() * offset - offset / 2.0D;
                double randOffsetY = entity.worldObj.rand.nextDouble() * offset - offset / 2.0D;
                double randOffsetZ = entity.worldObj.rand.nextDouble() * offset - offset / 2.0D;

                double particleX = entity.posX + randOffsetX;
                double centerY = (entity.posY + entity.height / 2.0D) + randOffsetY;
                double particleZ = entity.posZ + randOffsetZ;

                String particle = "reddust";

                GOTPacketHandler.networkWrapper.sendToAllAround(new LOTRPacketParticleFX(particle, 10, particleX, centerY, particleZ, 0.0D, 0.1D, 0.0D), GOTPacketHandler.nearEntity(entity, 64.0));
            }
        }
    }

    @Override
    public int getStatusIconIndex() {
        return 0;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
