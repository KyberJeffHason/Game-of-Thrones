package got.common.block;

import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class WOTRMCBlock extends Block {
	public WOTRMCBlock(Material p_i45394_1_) {
		super(p_i45394_1_);
		setHardness(1.5f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(GOTCreativeTabs.tabBlock);
	}
}
