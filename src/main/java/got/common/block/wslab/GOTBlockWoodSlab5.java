package got.common.block.wslab;

import cpw.mods.fml.relauncher.*;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.database.GOTRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockWoodSlab5 extends GOTBlockSlabBase {
	public GOTBlockWoodSlab5(boolean flag) {
		super(flag, Material.wood, 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.planks3.getIcon(i, j &= 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
