package got.common.world.biome.sothoryos;

import java.util.ArrayList;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.*;

public class GOTBiomeQarthColony extends GOTBiomeSothoryosSavannah {
	public GOTBiomeQarthColony(int i, boolean major) {
		super(i, major);
		setUnreliableChance(GOTEventSpawner.EventChance.COMMON);
		decorator.clearVillages();
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		npcSpawnList.clear();
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterQarthColony;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.SOTHORYOS.getSubregion("qarthColony");
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}