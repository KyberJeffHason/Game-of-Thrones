package got.client.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import got.common.handlers.StaminaServerHandler;
import got.rome.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GOTInterfaceHandler {

    public static final GOTInterfaceHandler INSTANCE = new GOTInterfaceHandler();
    private float interpolatedStamina = 0;

    @SubscribeEvent
    public void onOverlayPostMy(RenderGameOverlayEvent.Post e) {
        if (e.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        int w = e.resolution.getScaledWidth();
        int h = e.resolution.getScaledHeight();
        EntityClientPlayerMP player = mc.thePlayer;

        int aX = w / 2 - 10;
        int aY = h + 40;

        GL11.glPushMatrix();

        float disposX = 150;
        float disposY = 15;
        float scaleFactor = 1.7f;

        float newPosX = aX - 33 + disposX;
        float newPosY = aY - 100 + disposY;
        newPosX /= scaleFactor;
        newPosY /= scaleFactor;

        GL11.glScalef(scaleFactor, scaleFactor, 1.0f);

        mc.getTextureManager().bindTexture(new ResourceLocation("got:textures/hud/stamina_bar.png"));
        drawTextureCustomSize(newPosX, newPosY, 2, 10, 67, 13, 70, 30);

        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScalef(scaleFactor, scaleFactor, 1.0f);
        mc.getTextureManager().bindTexture(new ResourceLocation("got:textures/hud/stamina.png"));
        float staminaPosX = (aX - 23 + disposX) / scaleFactor;
        float staminaPosY = (aY - 91 + disposY) / scaleFactor;
        int fix = 0;
        float currentStamina = ExtendedPlayer.get(player).getStamina();
        if (currentStamina == StaminaServerHandler.MAX_STAMINA) {
            fix = 0;
        }
        interpolatedStamina += (currentStamina - interpolatedStamina) * 0.1f; // Adjust the 0.1f value for smoother or faster interpolation
        drawTextureCustomSize(staminaPosX, staminaPosY, 0, 0, (double) ((interpolatedStamina + fix) * 55) / StaminaServerHandler.MAX_STAMINA, 3, 55, 3);

        String resource = (int) 50 + "/" + (int) 100;
        mc.fontRenderer.drawString(resource, (int) (aX + 83 + disposX), (int) (aY - 83 + disposY), new Color(255, 255, 255).getRGB(), false);

        GL11.glPopMatrix();
    }

    public static void drawTextureCustomSize(double posX, double posY, double startPixX, double startPixY, double pieceSizeX, double pieceSizeY, float sizeTextureX, float sizeTextureY) {
        float f4 = 1.0F / sizeTextureX;
        float f5 = 1.0F / sizeTextureY;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX, posY + pieceSizeY, 0.0D, startPixX * f4, (startPixY + pieceSizeY) * f5);
        tessellator.addVertexWithUV(posX + pieceSizeX, posY + pieceSizeY, 0.0D, (startPixX + pieceSizeX) * f4, (startPixY + pieceSizeY) * f5);
        tessellator.addVertexWithUV(posX + pieceSizeX, posY, 0.0D, (startPixX + pieceSizeX) * f4, startPixY * f5);
        tessellator.addVertexWithUV(posX, posY, 0.0D, startPixX * f4, startPixY * f5);
        tessellator.draw();
    }

    public static void drawColoredSquare(double posX, double posY, double size, int color) {
        // Decode the color components from the integer color
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha); // Set the color for the square

        tessellator.startDrawingQuads();
        tessellator.addVertex(posX, posY + size, 0.0D); // Bottom left
        tessellator.addVertex(posX + size, posY + size, 0.0D); // Bottom right
        tessellator.addVertex(posX + size, posY, 0.0D); // Top right
        tessellator.addVertex(posX, posY, 0.0D); // Top left
        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}