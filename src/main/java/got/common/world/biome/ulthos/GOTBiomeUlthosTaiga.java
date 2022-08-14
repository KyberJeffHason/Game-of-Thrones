package got.common.world.biome.ulthos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntityBlizzard;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeUlthosTaiga extends GOTBiome {
	public GOTBiomeUlthosTaiga(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		topBlock = Blocks.grass;
		fillerBlock = Blocks.snow;
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBlizzard.class, 20, 3, 3));
		decorator.treesPerChunk = 10;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 20);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterUlthosTaiga;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ULTHOS.getSubregion("ulthosTaiga");
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.5f;
	}
}