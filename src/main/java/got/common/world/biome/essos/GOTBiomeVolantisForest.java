package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.feature.GOTTreeType;
import got.common.world.spawning.GOTEventSpawner;

public class GOTBiomeVolantisForest extends GOTBiomeVolantis {
	public GOTBiomeVolantisForest(int i, boolean major) {
		super(i, major);
		npcSpawnList.clear();
		decorator.clearVillages();
		invasionSpawns.clearInvasions();
		clearBiomeVariants();
		setupStandartForestFauna();
		enablePodzol = false;
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.OAK, 300);
		decorator.addTree(GOTTreeType.OAK_LARGE, 50);
		decorator.addTree(GOTTreeType.LARCH, 200);
		decorator.addTree(GOTTreeType.BEECH, 100);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.ORANGE, 730);
		decorator.addTree(GOTTreeType.ASPEN, 100);
		decorator.addTree(GOTTreeType.ASPEN_LARGE, 20);
		decorator.treesPerChunk = 10;
		decorator.flowersPerChunk = 6;
		decorator.grassPerChunk = 8;
		decorator.doubleGrassPerChunk = 2;
		decorator.whiteSand = true;
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterVolantisForest;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("volantisForest");
	}
}
