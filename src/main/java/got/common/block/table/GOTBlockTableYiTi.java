package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableYiTi extends GOTBlockCraftingTable {
	public GOTBlockTableYiTi() {
		super(Material.wood, GOTFaction.HIGH_POWER, 49);
		setStepSound(Block.soundTypeWood);
	}
}
