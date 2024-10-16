package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.world.*;

public class GOTBlockGlass extends BlockGlass {
	public boolean thirdParam = false;

	public GOTBlockGlass() {
		super(Material.glass, false);
		setHardness(0.3f);
		setStepSound(Block.soundTypeGlass);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
		return true;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		blockIcon = iconregister.registerIcon(getTextureName());
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
		Block block = world.getBlock(i, j, k);
		if (world.getBlockMetadata(i, j, k) != world.getBlockMetadata(i - Facing.offsetsXForSide[side], j - Facing.offsetsYForSide[side], k - Facing.offsetsZForSide[side])) {
			return true;
		}
		if (block == this) {
			return false;
		}
		return !thirdParam && block == this ? false : super.shouldSideBeRendered(world, i, j, k, side);
	}
}
