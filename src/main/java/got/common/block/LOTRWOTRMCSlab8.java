package got.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.block.slab.GOTBlockSlabBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRWOTRMCSlab8 extends GOTBlockSlabBase {
	public LOTRWOTRMCSlab8(boolean flag) {
		super(flag, Material.rock, 8);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if ((j &= 7) == 0)
			return WOTRMCNewBlocks.gondorbrick.getIcon(i, 0);
		if (j == 1)
			return WOTRMCNewBlocks.gondorbrickaligned.getIcon(i, 0);
		if (j == 2)
			return WOTRMCNewBlocks.gondordetailedbrick.getIcon(i, 0);
		if (j == 3)
			return WOTRMCNewBlocks.gondorlargebricks.getIcon(i, 0);
		if (j == 4)
			return WOTRMCNewBlocks.gondorplate.getIcon(i, 0);
		if (j == 5)
			return WOTRMCNewBlocks.gondorsmallbrick.getIcon(i, 0);
		if (j == 6)
			return WOTRMCNewBlocks.highelfborder.getIcon(i, 0);
		return WOTRMCNewBlocks.jinzhunbrick.getIcon(i, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
