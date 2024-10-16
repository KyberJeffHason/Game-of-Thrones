package got.common.database;

import java.util.*;

import got.common.entity.animal.*;
import got.common.entity.essos.gold.*;
import got.common.entity.other.*;
import got.common.entity.westeros.arryn.*;
import got.common.entity.westeros.crownlands.*;
import got.common.entity.westeros.dorne.*;
import got.common.entity.westeros.dragonstone.*;
import got.common.entity.westeros.gift.*;
import got.common.entity.westeros.hillmen.*;
import got.common.entity.westeros.ironborn.*;
import got.common.entity.westeros.north.*;
import got.common.entity.westeros.north.hillmen.*;
import got.common.entity.westeros.reach.*;
import got.common.entity.westeros.riverlands.*;
import got.common.entity.westeros.stormlands.*;
import got.common.entity.westeros.westerlands.*;
import got.common.entity.westeros.wildling.*;
import got.common.entity.westeros.wildling.thenn.*;

public class GOTUnitTradeEntries {
	public static int LEVYMAN = 5;
	public static float LEVYMAN_F;
	public static int LEVYMANA = 10;
	public static float LEVYMANA_F = 5.0f;
	public static int SOLDIER = 10;
	public static float SOLDIER_F = 5.0f;
	public static int SOLDIERA = 15;
	public static float SOLDIERA_F = 10.0f;
	public static int SOLDIERH = 15;
	public static float SOLDIERH_F = 10.0f;
	public static int SOLDIERHA = 20;
	public static float SOLDIERHA_F = 15.0f;
	public static int SLAVE = 10;
	public static float SLAVE_F;

	public static List<GOTUnitTradeEntry> ARRYN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> DORNE_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> DRAGONSTONE_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> IRONBORN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> NORTH_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> REACH_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> RIVERLANDS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> STORMLANDS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> WESTERLANDS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> YITI_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> KINGSGUARD_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> ASSHAI_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> CROWNLANDS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> CROWNLANDS_GUARDIAN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> WILDLING_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> SUMMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> THENN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> SOTHORYOS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> NORTH_HILLMEN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> GIFT_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> GOLDENCOMPANY_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> IBBEN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> JOGOS_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> HILLMEN_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> ARRYN_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> CROWNLANDS_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> DORNE_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> DRAGONSTONE_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> IBBEN_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> IRONBORN_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> NORTH_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> REACH_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> RIVERLANDS_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> STORMLANDS_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> WESTERLANDS_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> YITI_FARMER_L = new ArrayList<>();
	public static List<GOTUnitTradeEntry> PROSTITUTE_KEEPER_L = new ArrayList<>();

	static {
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynLevyman.class, LEVYMAN, LEVYMAN_F));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynSoldier.class, SOLDIER, SOLDIER_F));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynBannerBearer.class, SOLDIER, SOLDIER_F));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		ARRYN_L.add(new GOTUnitTradeEntry(GOTEntityArrynGuard.class, SOLDIER + 5, SOLDIER_F + 5.0f));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneLevyman.class, LEVYMAN, LEVYMAN_F));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneSoldier.class, SOLDIER, SOLDIER_F));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneBannerBearer.class, SOLDIER, SOLDIER_F));
		DORNE_L.add(new GOTUnitTradeEntry(GOTEntityDorneBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneLevyman.class, LEVYMAN, LEVYMAN_F));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldier.class, SOLDIER, SOLDIER_F));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneBannerBearer.class, SOLDIER, SOLDIER_F));
		DRAGONSTONE_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		IRONBORN_L.add(new GOTUnitTradeEntry(GOTEntityIronbornLevyman.class, LEVYMAN, LEVYMAN_F));
		IRONBORN_L.add(new GOTUnitTradeEntry(GOTEntityIronbornLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		IRONBORN_L.add(new GOTUnitTradeEntry(GOTEntityIronbornSoldier.class, SOLDIER, SOLDIER_F));
		IRONBORN_L.add(new GOTUnitTradeEntry(GOTEntityIronbornSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		IRONBORN_L.add(new GOTUnitTradeEntry(GOTEntityIronbornBannerBearer.class, SOLDIER, SOLDIER_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthLevyman.class, LEVYMAN, LEVYMAN_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthSoldier.class, SOLDIER, SOLDIER_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthBannerBearer.class, SOLDIER, SOLDIER_F));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		NORTH_L.add(new GOTUnitTradeEntry(GOTEntityNorthGuard.class, SOLDIER + 5, SOLDIER_F + 5.0f));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachLevyman.class, LEVYMAN, LEVYMAN_F));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachSoldier.class, SOLDIER, SOLDIER_F));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachBannerBearer.class, SOLDIER, SOLDIER_F));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		REACH_L.add(new GOTUnitTradeEntry(GOTEntityReachGuard.class, SOLDIER + 5, SOLDIER_F + 5.0f));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldier.class, SOLDIER, SOLDIER_F));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		RIVERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldier.class, SOLDIER, SOLDIER_F));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		STORMLANDS_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldier.class, SOLDIER, SOLDIER_F));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldierArcher.class, SOLDIERA, SOLDIERA_F));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsSoldier.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		WESTERLANDS_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsGuard.class, SOLDIER + 5, SOLDIER_F + 5.0f));
		KINGSGUARD_L.add(new GOTUnitTradeEntry(GOTEntityKingsguard.class, SOLDIER + 5, SOLDIER_F + 5.0f).setPledgeExclusive());
		CROWNLANDS_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsLevyman.class, LEVYMAN, LEVYMAN_F));
		CROWNLANDS_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsLevymanArcher.class, LEVYMANA, LEVYMANA_F));
		CROWNLANDS_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsBannerBearer.class, SOLDIER, SOLDIER_F));
		CROWNLANDS_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsBannerBearer.class, GOTEntityHorse.class, "Rider", SOLDIERH, SOLDIERH_F).setMountArmor(GOTRegistry.westerosHorseArmor));
		CROWNLANDS_GUARDIAN_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsGuard.class, SOLDIER, SOLDIER_F));
		WILDLING_L.add(new GOTUnitTradeEntry(GOTEntityWildling.class, LEVYMAN, LEVYMAN_F));
		WILDLING_L.add(new GOTUnitTradeEntry(GOTEntityWildlingArcher.class, LEVYMANA, LEVYMANA_F));
		WILDLING_L.add(new GOTUnitTradeEntry(GOTEntityWildlingAxeThrower.class, LEVYMANA, LEVYMANA_F));
		WILDLING_L.add(new GOTUnitTradeEntry(GOTEntityWildlingBannerBearer.class, LEVYMAN, LEVYMAN_F));
		WILDLING_L.add(new GOTUnitTradeEntry(GOTEntityGiant.class, SOLDIER + 100, SOLDIER_F + 100.0f).setPledgeExclusive());
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityThenn.class, LEVYMAN, LEVYMAN_F));
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityThennArcher.class, LEVYMANA, LEVYMANA_F));
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityThennAxeThrower.class, LEVYMANA, LEVYMANA_F));
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityThennBannerBearer.class, LEVYMAN, LEVYMAN_F));
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityThennBerserker.class, SOLDIER + 30, SOLDIER_F + 30.0f).setPledgeExclusive());
		THENN_L.add(new GOTUnitTradeEntry(GOTEntityGiant.class, SOLDIER + 100, SOLDIER_F + 100.0f).setPledgeExclusive());
		NORTH_HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanWarrior.class, SOLDIER, SOLDIER_F));
		NORTH_HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanArcher.class, SOLDIERA, SOLDIERA_F));
		NORTH_HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanAxeThrower.class, SOLDIERA, SOLDIERA_F));
		NORTH_HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanBannerBearer.class, SOLDIER, SOLDIER_F));
		NORTH_HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityNorthHillmanWarrior.class, GOTEntityWoolyRhino.class, "Rider", SOLDIERH + 30, SOLDIERH_F + 30.0f).setPledgeExclusive());
		GIFT_L.add(new GOTUnitTradeEntry(GOTEntityGiftGuard.class, SOLDIER, SOLDIER_F).setPledgeExclusive());
		GIFT_L.add(new GOTUnitTradeEntry(GOTEntityGiftBannerBearer.class, SOLDIER, SOLDIER_F).setPledgeExclusive());
		GOLDENCOMPANY_L.add(new GOTUnitTradeEntry(GOTEntityGoldenWarrior.class, SOLDIER, 0.0f));
		GOLDENCOMPANY_L.add(new GOTUnitTradeEntry(GOTEntityGoldenSpearman.class, SOLDIER, 0.0f));
		GOLDENCOMPANY_L.add(new GOTUnitTradeEntry(GOTEntityGoldenBannerBearer.class, SOLDIER, 0.0f));
		HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityHillmanWarrior.class, SOLDIER, SOLDIER_F));
		HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityHillmanArcher.class, SOLDIERA, SOLDIERA_F));
		HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityHillmanAxeThrower.class, SOLDIERA, SOLDIERA_F));
		HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityHillmanBannerBearer.class, SOLDIER, SOLDIER_F));
		HILLMEN_L.add(new GOTUnitTradeEntry(GOTEntityHillmanBerserker.class, SOLDIER + 30, SOLDIER_F + 30.0f).setPledgeExclusive());
		ARRYN_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityArrynFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		CROWNLANDS_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityCrownlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		DORNE_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityDorneFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		DRAGONSTONE_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityDragonstoneFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		IRONBORN_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityIronbornFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		NORTH_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityNorthFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		REACH_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityReachFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		RIVERLANDS_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityRiverlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		STORMLANDS_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityStormlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		WESTERLANDS_FARMER_L.add(new GOTUnitTradeEntry(GOTEntityWesterlandsFarmhand.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.FARMER));
		PROSTITUTE_KEEPER_L.add(new GOTUnitTradeEntry(GOTEntityProstitute.class, SLAVE, SLAVE_F).setTask(GOTHiredNPCInfo.Task.PROSTITUTE));
	}

	public static GOTUnitTradeEntries ARRYN = new GOTUnitTradeEntries(50.0f, ARRYN_L);
	public static GOTUnitTradeEntries DORNE = new GOTUnitTradeEntries(50.0f, DORNE_L);
	public static GOTUnitTradeEntries DRAGONSTONE = new GOTUnitTradeEntries(50.0f, DRAGONSTONE_L);
	public static GOTUnitTradeEntries IRONBORN = new GOTUnitTradeEntries(50.0f, IRONBORN_L);
	public static GOTUnitTradeEntries NORTH = new GOTUnitTradeEntries(50.0f, NORTH_L);
	public static GOTUnitTradeEntries REACH = new GOTUnitTradeEntries(50.0f, REACH_L);
	public static GOTUnitTradeEntries RIVERLANDS = new GOTUnitTradeEntries(50.0f, RIVERLANDS_L);
	public static GOTUnitTradeEntries STORMLANDS = new GOTUnitTradeEntries(50.0f, STORMLANDS_L);
	public static GOTUnitTradeEntries WESTERLANDS = new GOTUnitTradeEntries(50.0f, WESTERLANDS_L);
	public static GOTUnitTradeEntries YITI = new GOTUnitTradeEntries(50.0f, YITI_L);
	public static GOTUnitTradeEntries KINGSGUARD = new GOTUnitTradeEntries(50.0f, KINGSGUARD_L);
	public static GOTUnitTradeEntries ASSHAI = new GOTUnitTradeEntries(50.0f, ASSHAI_L);
	public static GOTUnitTradeEntries CROWNLANDS = new GOTUnitTradeEntries(50.0f, CROWNLANDS_L);
	public static GOTUnitTradeEntries CROWNLANDS_GUARDIAN = new GOTUnitTradeEntries(50.0f, CROWNLANDS_GUARDIAN_L);
	public static GOTUnitTradeEntries WILDLING = new GOTUnitTradeEntries(50.0f, WILDLING_L);
	public static GOTUnitTradeEntries SUMMER = new GOTUnitTradeEntries(50.0f, SUMMER_L);
	public static GOTUnitTradeEntries THENN = new GOTUnitTradeEntries(50.0f, THENN_L);
	public static GOTUnitTradeEntries SOTHORYOS = new GOTUnitTradeEntries(50.0f, SOTHORYOS_L);
	public static GOTUnitTradeEntries NORTH_HILLMEN = new GOTUnitTradeEntries(50.0f, NORTH_HILLMEN_L);
	public static GOTUnitTradeEntries GIFT = new GOTUnitTradeEntries(50.0f, GIFT_L);
	public static GOTUnitTradeEntries GOLDENCOMPANY = new GOTUnitTradeEntries(0.0f, GOLDENCOMPANY_L);
	public static GOTUnitTradeEntries IBBEN = new GOTUnitTradeEntries(50.0f, IBBEN_L);
	public static GOTUnitTradeEntries JOGOS = new GOTUnitTradeEntries(50.0f, JOGOS_L);
	public static GOTUnitTradeEntries HILLMEN = new GOTUnitTradeEntries(50.0f, HILLMEN_L);
	public static GOTUnitTradeEntries ARRYN_FARMER = new GOTUnitTradeEntries(0.0f, ARRYN_FARMER_L);
	public static GOTUnitTradeEntries CROWNLANDS_FARMER = new GOTUnitTradeEntries(0.0f, CROWNLANDS_FARMER_L);
	public static GOTUnitTradeEntries DORNE_FARMER = new GOTUnitTradeEntries(0.0f, DORNE_FARMER_L);
	public static GOTUnitTradeEntries DRAGONSTONE_FARMER = new GOTUnitTradeEntries(0.0f, DRAGONSTONE_FARMER_L);
	public static GOTUnitTradeEntries IBBEN_FARMER = new GOTUnitTradeEntries(0.0f, IBBEN_FARMER_L);
	public static GOTUnitTradeEntries IRONBORN_FARMER = new GOTUnitTradeEntries(0.0f, IRONBORN_FARMER_L);
	public static GOTUnitTradeEntries NORTH_FARMER = new GOTUnitTradeEntries(0.0f, NORTH_FARMER_L);
	public static GOTUnitTradeEntries REACH_FARMER = new GOTUnitTradeEntries(0.0f, REACH_FARMER_L);
	public static GOTUnitTradeEntries STORMLANDS_FARMER = new GOTUnitTradeEntries(0.0f, STORMLANDS_FARMER_L);
	public static GOTUnitTradeEntries WESTERLANDS_FARMER = new GOTUnitTradeEntries(0.0f, WESTERLANDS_FARMER_L);
	public static GOTUnitTradeEntries YITI_FARMER = new GOTUnitTradeEntries(0.0f, YITI_FARMER_L);
	public static GOTUnitTradeEntries PROSTITUTE_KEEPER = new GOTUnitTradeEntries(0.0f, PROSTITUTE_KEEPER_L);

	public GOTUnitTradeEntry[] tradeEntries;

	public GOTUnitTradeEntries(float baseAlignment, List<GOTUnitTradeEntry> list) {
		GOTUnitTradeEntry[] arr = new GOTUnitTradeEntry[list.size()];
		arr = list.toArray(arr);
		for (GOTUnitTradeEntry trade : tradeEntries = arr) {
			trade.alignmentRequired += baseAlignment;
			if (trade.alignmentRequired >= 0.0f) {
				continue;
			}
			throw new IllegalArgumentException("Units cannot require negative alignment!");
		}
	}
}
