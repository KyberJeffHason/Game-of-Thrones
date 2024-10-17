package got.client.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.common.GOTConfig;
import got.common.handlers.BlockServerHandler;
import got.common.systems.GOTCoreBlockingSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.Timer;

public class GOTBlockClientHandler {

    public static final GOTBlockClientHandler INSTANCE = new GOTBlockClientHandler();


    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (BlockServerHandler.INSTANCE.isBlocking(player)) {
            float[] blockAngles = GOTCoreBlockingSystem.getBlockAngles(player.getHeldItem().getItem().getClass());
            if(GOTConfig.enableBlockArc) {
                renderBlockAngleOverlay(blockAngles);
            }
        }
    }

    private void renderBlockAngleOverlay(float[] blockAngles) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        // Use reflection to access the private timer field
        float partialTicks = 0.0f;
        try {
            Field timerField = Minecraft.class.getDeclaredField("timer");
            timerField.setAccessible(true);
            Object timer = timerField.get(mc);
            Field renderPartialTicksField = timer.getClass().getDeclaredField("renderPartialTicks");
            renderPartialTicksField.setAccessible(true);
            partialTicks = renderPartialTicksField.getFloat(timer);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        double playerX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        double playerY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
        double playerZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

        double radius = 2.0; // Radius of the arc
        double centerX = playerX;
        double centerY = playerY + player.getEyeHeight();
        double centerZ = playerZ;
        double startAngle = -blockAngles[0];
        double endAngle = blockAngles[1];

        float playerYaw = player.rotationYaw + 90;

        GL11.glPushMatrix(); // Save the current matrix
        GL11.glTranslated(-playerX, -centerY, -playerZ); // Translate to player's position

        for (double angle = startAngle; angle <= endAngle; angle += 10.0) { // Increase step size to reduce particles
            double rad = Math.toRadians(angle);
            double x = centerX + Math.cos(rad) * radius;
            double z = centerZ + Math.sin(rad) * radius;

            // Rotate the point around the player's position
            double rotatedX = playerX + (x - playerX) * Math.cos(Math.toRadians(playerYaw)) - (z - playerZ) * Math.sin(Math.toRadians(playerYaw));
            double rotatedZ = playerZ + (x - playerX) * Math.sin(Math.toRadians(playerYaw)) + (z - playerZ) * Math.cos(Math.toRadians(playerYaw));

            mc.theWorld.spawnParticle("flame", rotatedX, centerY, rotatedZ, 0.0, 0.0, 0.0); // Use "smoke" particles
        }

        GL11.glPopMatrix(); // Restore the previous matrix
    }

}
