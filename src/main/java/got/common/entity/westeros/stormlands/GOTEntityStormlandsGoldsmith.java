package got.common.entity.westeros.stormlands;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityStormlandsGoldsmith extends GOTEntityStormlandsMarketTrader {
	public GOTEntityStormlandsGoldsmith(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTTradeEntries getBuyPool() {
		return GOTTradeEntries.C_GOLDSMITH_BUY;
	}

	@Override
	public GOTTradeEntries getSellPool() {
		return GOTTradeEntries.C_GOLDSMITH_SELL;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		npcItemsInv.setIdleItem(new ItemStack(GOTRegistry.silverRing));
		return data;
	}
}