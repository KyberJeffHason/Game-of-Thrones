package got.common.block.slab;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockSlabClayTileDyed extends GOTBlockSlabBase {
	public GOTBlockSlabClayTileDyed(boolean flag) {
		super(flag, Material.rock, 8);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.25f);
		setResistance(7.0f);
		setStepSound(Block.soundTypeStone);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return GOTRegistry.clayTileDyed.getIcon(i, j &= 7);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}
}
