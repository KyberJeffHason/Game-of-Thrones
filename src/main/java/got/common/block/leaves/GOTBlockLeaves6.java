package got.common.block.leaves;

import java.util.*;

import got.common.database.GOTRegistry;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class GOTBlockLeaves6 extends GOTBlockLeavesBase {
	public GOTBlockLeaves6() {
		setLeafNames("mahogany", "willow", "cypress", "olive");
	}

	@Override
	public void addSpecialLeafDrops(ArrayList drops, World world, int i, int j, int k, int meta, int fortune) {
		if ((meta & 3) == 3 && world.rand.nextInt(calcFortuneModifiedDropChance(10, fortune)) == 0) {
			drops.add(new ItemStack(GOTRegistry.olive));
		}
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(GOTRegistry.sapling6);
	}
}
