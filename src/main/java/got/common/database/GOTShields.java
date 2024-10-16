package got.common.database;

import java.util.*;

import got.GOT;
import got.common.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public enum GOTShields {
	NORTH(GOTFaction.NORTH), NORTHGUARD(GOTFaction.NORTH), RIVERLANDS(GOTFaction.RIVERLANDS), ARRYN(GOTFaction.ARRYN), ARRYNGUARD(GOTFaction.ARRYN), HILLMEN(GOTFaction.HILL_TRIBES), IRONBORN(GOTFaction.IRONBORN), WESTERLANDS(GOTFaction.WESTERLANDS), WESTERLANDSGUARD(GOTFaction.WESTERLANDS), DRAGONSTONE(GOTFaction.DRAGONSTONE), CROWNLANDS(GOTFaction.CROWNLANDS), STORMLANDS(GOTFaction.STORMLANDS), REACH(GOTFaction.REACH), REACHGUARD(GOTFaction.REACH), DORNE(GOTFaction.DORNE), VOLANTIS(GOTFaction.HIGH_POWER), PENTOS(GOTFaction.HIGH_POWER), NORVOS(GOTFaction.HIGH_POWER), BRAAVOS(GOTFaction.HIGH_POWER), TYROSH(GOTFaction.HIGH_POWER), LORATH(GOTFaction.HIGH_POWER), QOHOR(GOTFaction.HIGH_POWER), LYS(GOTFaction.HIGH_POWER), MYR(GOTFaction.HIGH_POWER), QARTH(GOTFaction.HIGH_POWER), GHISCAR(GOTFaction.HIGH_POWER), UNSULLIED(GOTFaction.HIGH_POWER), YITI(GOTFaction.HIGH_POWER), YITI_FRONTIER(GOTFaction.HIGH_POWER), YITI_SAMURAI(GOTFaction.HIGH_POWER), ASSHAI(GOTFaction.HIGH_POWER), SUMMER(GOTFaction.HIGH_POWER), SOTHORYOS(GOTFaction.HIGH_POWER), GOLDENCOMPANY, ALCOHOLIC, ACHIEVEMENT_BRONZE, ACHIEVEMENT_SILVER, ACHIEVEMENT_GOLD, ACHIEVEMENT_VALYRIAN, TARGARYEN(false, GOT.devs);

	public ShieldType shieldType;
	public int shieldID;
	public UUID[] exclusiveUUIDs;
	public GOTFaction alignmentFaction;
	public ResourceLocation shieldTexture;
	public boolean isHidden;

	GOTShields() {
		this(ShieldType.ACHIEVABLE, false, new ArrayList<>());
	}

	GOTShields(boolean hidden, List<String> players) {
		this(ShieldType.EXCLUSIVE, hidden, players);
	}

	GOTShields(GOTFaction faction) {
		this(ShieldType.ALIGNMENT, false, new ArrayList<>());
		alignmentFaction = faction;
	}

	GOTShields(ShieldType type, boolean hidden, List<String> players) {
		shieldType = type;
		shieldID = shieldType.list.size();
		shieldType.list.add(this);
		shieldTexture = new ResourceLocation("got:textures/shield/" + name().toLowerCase() + ".png");
		exclusiveUUIDs = new UUID[players.size()];
		for (int i = 0; i < players.size(); ++i) {
			String s = players.get(i);
			exclusiveUUIDs[i] = UUID.fromString(s);
		}
		isHidden = hidden;
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerWear(entityplayer);
	}

	public boolean canPlayerWear(EntityPlayer entityplayer) {
		if (shieldType == ShieldType.ALIGNMENT) {
			return GOTLevelData.getData(entityplayer).getAlignment(alignmentFaction) >= 100.0f;
		}
		if (this == ACHIEVEMENT_BRONZE) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 10;
		}
		if (this == ACHIEVEMENT_SILVER) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 20;
		}
		if (this == ACHIEVEMENT_GOLD) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 50;
		}
		if (this == ACHIEVEMENT_VALYRIAN) {
			return GOTLevelData.getData(entityplayer).getEarnedAchievements(GOTDimension.GAME_OF_THRONES).size() >= 100;
		}
		if (this == ALCOHOLIC) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.gainHighAlcoholTolerance);
		}
		if (this == GOLDENCOMPANY) {
			return GOTLevelData.getData(entityplayer).hasAchievement(GOTAchievement.hireGoldenCompany);
		}
		if (shieldType == ShieldType.EXCLUSIVE) {
			for (UUID uuid : exclusiveUUIDs) {
				if (!uuid.equals(entityplayer.getUniqueID())) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public String getShieldDesc() {
		if (shieldType == ShieldType.ALIGNMENT) {
			return StatCollector.translateToLocal("got.attribute.desc");
		}
		return StatCollector.translateToLocal("got.shields." + name() + ".desc");
	}

	public String getShieldName() {
		return StatCollector.translateToLocal("got.shields." + name() + ".name");
	}

	public static void preInit() {
	}

	public static GOTShields shieldForName(String shieldName) {
		for (GOTShields shield : GOTShields.values()) {
			if (!shield.name().equals(shieldName)) {
				continue;
			}
			return shield;
		}
		return null;
	}

	public enum ShieldType {
		ALIGNMENT, ACHIEVABLE, EXCLUSIVE;

		public List<GOTShields> list = new ArrayList<>();

		public String getDisplayName() {
			return StatCollector.translateToLocal("got.shields.category." + name());
		}
	}

}