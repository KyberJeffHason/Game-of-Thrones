package got.common.entity.essos.gold;

import got.common.database.GOTRegistry;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenSpearman extends GOTEntityGoldenWarrior {
	public GOTEntityGoldenSpearman(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPike));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		return data;
	}
}
