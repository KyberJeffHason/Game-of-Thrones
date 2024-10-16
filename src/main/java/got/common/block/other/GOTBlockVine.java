package got.common.block.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.world.IBlockAccess;

public class GOTBlockVine extends BlockVine {
	public GOTBlockVine() {
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setHardness(0.2f);
		setStepSound(Block.soundTypeGrass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		return 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getBlockColor() {
		return 16777215;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		return 16777215;
	}
}
