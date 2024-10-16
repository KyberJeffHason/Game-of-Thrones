package got.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class GOTModelLionRug extends ModelBase {
	public GOTModelLion lionModel = new GOTModelLion();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles();
		GL11.glTranslatef(0.0f, -0.4f, 0.0f);
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 0.4f, 1.0f);
		lionModel.body.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		lionModel.tail.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, -0.1f, 0.1f);
		lionModel.head.render(f5);
		lionModel.mane.render(f5);
		GL11.glPopMatrix();
		GL11.glTranslatef(0.0f, 0.15f, 0.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.4f, 0.0f, 0.0f);
		lionModel.leg1.render(f5);
		lionModel.leg3.render(f5);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef(0.4f, 0.0f, 0.0f);
		lionModel.leg2.render(f5);
		lionModel.leg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotationAngles() {
		lionModel.leg1.rotateAngleX = (float) Math.toRadians(30.0);
		lionModel.leg1.rotateAngleZ = (float) Math.toRadians(90.0);
		lionModel.leg2.rotateAngleX = (float) Math.toRadians(30.0);
		lionModel.leg2.rotateAngleZ = (float) Math.toRadians(-90.0);
		lionModel.leg3.rotateAngleX = (float) Math.toRadians(-20.0);
		lionModel.leg3.rotateAngleZ = (float) Math.toRadians(90.0);
		lionModel.leg4.rotateAngleX = (float) Math.toRadians(-20.0);
		lionModel.leg4.rotateAngleZ = (float) Math.toRadians(-90.0);
	}
}
