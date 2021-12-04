package got.common.world.biome.westeros;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.*;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureRuins;
import got.common.world.structure.westeros.stormlands.GOTStructureStormlandsCity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;

public class GOTBiomeStormlands extends GOTBiomeWesteros {
	public GOTBiomeStormlands(int i, boolean major) {
		super(i, major);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FIELD_CORN, 0.2f);
		decorator.affix(new GOTStructureStormlandsCity(this, 1.0f));
		invasionSpawns.addInvasion(GOTInvasions.WESTERLANDS, GOTEventSpawner.EventChance.UNCOMMON);
		invasionSpawns.addInvasion(GOTInvasions.DRAGONSTONE, GOTEventSpawner.EventChance.UNCOMMON);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.DRAGONSTONE_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		SpawnListContainer[] container2 = new SpawnListContainer[1];
		container2[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WESTERLANDS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container2);
		SpawnListContainer[] container3 = new SpawnListContainer[1];
		container3[0] = GOTBiomeSpawnList.entry(GOTSpawnList.WALKERS_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container3);
		GOTStructureRuins ruinsGen = new GOTStructureRuins(this, 0.0f);
		ruinsGen.affix(GOTWaypoint.Summerhall);
		ruinsGen.affix(GOTWaypoint.TowerOfJoy);
		decorator.affix(ruinsGen);
		SpawnListContainer[] container0 = new SpawnListContainer[2];
		container0[0] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_CIVILIAN, 10).setSpawnChance(GOTBiome.SPAWN);
		container0[1] = GOTBiomeSpawnList.entry(GOTSpawnList.STORMLANDS_MILITARY, 4).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(10).add(container0);
		SpawnListContainer[] containerLSR = new SpawnListContainer[1];
		containerLSR[0] = GOTBiomeSpawnList.entry(GOTSpawnList.UNRELIABLE, 10).setSpawnChance(GOTBiome.SPAWN);
		npcSpawnList.newFactionList(1).add(containerLSR);
		GOTStructureStormlandsCity castle = new GOTStructureStormlandsCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Nightsong, 0, 1);
		castle.affix(GOTWaypoint.Poddingfield);
		castle.affix(GOTWaypoint.HarvestHall);
		castle.affix(GOTWaypoint.Fawntown);
		castle.affix(GOTWaypoint.Blackhaven, -1, 0);
		castle.affix(GOTWaypoint.DeatsHear);
		castle.affix(GOTWaypoint.Stonehelm);
		castle.affix(GOTWaypoint.BlackHeart);
		castle.affix(GOTWaypoint.SeaworthCastle);
		castle.affix(GOTWaypoint.Amberly);
		castle.affix(GOTWaypoint.RainHouse);
		castle.affix(GOTWaypoint.Mistwood);
		castle.affix(GOTWaypoint.Greenstone);
		castle.affix(GOTWaypoint.TudburyHoll);
		castle.affix(GOTWaypoint.Bronzegate, 1, 0);
		castle.affix(GOTWaypoint.Felwood, 0, 1);
		castle.affix(GOTWaypoint.Grandview);
		castle.affix(GOTWaypoint.HaystackHall);
		castle.affix(GOTWaypoint.Gallowsgrey);
		castle.affix(GOTWaypoint.Parchments);
		castle.affix(GOTWaypoint.BroadArch);
		castle.affix(GOTWaypoint.EvenfallHall);
		castle.affix(GOTWaypoint.StormsEnd);
		decorator.affix(castle);
		GOTStructureStormlandsCity town = new GOTStructureStormlandsCity(this, 0.0f).setIsTown();
		town.affix(GOTWaypoint.WeepingTown);
		decorator.affix(town);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		int k1;
		int j1;
		int i1;
		super.decorate(world, random, i, k);
		if (random.nextInt(6) == 0) {
			i1 = i + random.nextInt(16) + 8;
			j1 = random.nextInt(128);
			k1 = k + random.nextInt(16) + 8;
			new WorldGenFlowers(GOTRegistry.pipeweedPlant).generate(world, random, i1, j1, k1);
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_STORMLANDS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("stormlands");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.STORMLANDS;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
