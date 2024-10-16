package got.common.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.*;
import cpw.mods.fml.relauncher.*;
import got.client.GOTTextures;
import got.common.*;
import got.common.GOTDimension.DimensionRegion;
import got.common.database.*;
import got.common.faction.*;
import got.common.item.other.GOTItemBanner;
import got.common.quest.GOTMiniQuestFactory;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.genlayer.GOTGenLayerWorld;
import got.common.world.map.*;
import got.common.world.map.GOTRoads.RoadPointDatabase;
import got.common.world.map.GOTWalls.WallPointDatabase;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class GOTAPI {
	/**
	 * @apiNote Creates new achievement category.
	 * @param enumName - name of the new achievement category in enum.
	 * @param biome    - biome name turns into the category name.
	 */
	public static GOTAchievement.Category addAchievementCategory(String enumName, GOTBiome biome) {
		Class[] classArr = { GOTBiome.class };
		Object[] args = { biome };
		return EnumHelper.addEnum(GOTAchievement.Category.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new achievement category.
	 * @param enumName - name of the new achievement category in enum.
	 * @param faction  - faction name turns into the category name.
	 */
	public static GOTAchievement.Category addAchievementCategory(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTAchievement.Category.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction cape.
	 * @param enumName - name of the new cape in enum.
	 * @param faction  - cape belongs to this faction.
	 */
	public static GOTCapes addAlignmentCape(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction shield.
	 * @param enumName - name of the new shield in enum.
	 * @param faction  - shield belongs to this faction.
	 */
	public static GOTShields addAlignmentShield(String enumName, GOTFaction faction) {
		Class[] classArr = { GOTFaction.class };
		Object[] args = { faction };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction banner.
	 * @param enumName   - name of the new banner in enum.
	 * @param id         - should be unique, so use ids from 700.
	 * @param bannerName - texture name of the banner.
	 * @param faction    - banner's faction. Affects on private, conquest, icon
	 *                   color.
	 */
	public static GOTItemBanner.BannerType addBanner(String enumName, int id, String bannerName, GOTFaction faction) {
		Class[] classArr = { Integer.TYPE, String.class, GOTFaction.class };
		Object[] args = { id, bannerName, faction };
		GOTItemBanner.BannerType banner = EnumHelper.addEnum(GOTItemBanner.BannerType.class, enumName, classArr, args);
		GOTItemBanner.BannerType.bannerForID.put(banner.bannerID, banner);
		GOTItemBanner.BannerType.bannerTypes.add(banner);
		return banner;
	}

	/**
	 * @apiNote Creates new exclusive cape.
	 * @param enumName - name of the new cape in enum.
	 * @param hidden   - will be displayed in the GUI or not.
	 * @param players  - UUIDs of the owners.
	 */
	public static GOTCapes addCape(String enumName, boolean hidden, List<String> players) {
		return addCape(enumName, GOTCapes.CapeType.EXCLUSIVE, hidden, players);
	}

	/**
	 * @apiNote Creates new exclusive cape.
	 * @param enumName - name of the new cape in enum.
	 * @param type     - exclusive/achievement/faction.
	 * @param hidden   - will be displayed in the GUI or not.
	 * @param players  - UUIDs of the owners.
	 */
	public static GOTCapes addCape(String enumName, GOTCapes.CapeType type, boolean hidden, List<String> players) {
		Class[] classArr = { GOTCapes.CapeType.class, Boolean.TYPE, ArrayList.class };
		Object[] args = { type, hidden, players };
		return EnumHelper.addEnum(GOTCapes.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new dimension region.
	 * @param enumName   - name of the new dimension region in enum.
	 * @param regionName - name of the region, should be translated.
	 */
	public static DimensionRegion addDimensionRegion(String enumName, String regionName) {
		Class[] classArr = { String.class };
		Object[] args = { regionName };
		return EnumHelper.addEnum(DimensionRegion.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction
	 * @param enumName - name of the new faction in enum.
	 * @param color    - 0xHHEEXX. For the bar, banners, eggs, etc.
	 * @param region   - dimension region.
	 * @param mapInfo  - square of the map displayed on the faction page.
	 */
	public static GOTFaction addFaction(String enumName, int color, DimensionRegion region, GOTMapRegion mapInfo) {
		Class[] classArr = { Integer.TYPE, GOTDimension.class, DimensionRegion.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, GOTMapRegion.class };
		Object[] args = { color, GOTDimension.GAME_OF_THRONES, region, true, true, Integer.MIN_VALUE, mapInfo };
		return EnumHelper.addEnum(GOTFaction.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction with lots of technical settings.
	 * @deprecated Complex way, only for advanced developers.
	 * @param enumName  - name of the new faction in enum.
	 * @param color     - 0xHHEEXX. For the bar, banners, eggs, etc.
	 * @param dim       - dimension.
	 * @param region    - dimension region.
	 * @param player    - playability (reputation gaining, ...).
	 * @param registry  - allows entity registry (attack settings).
	 * @param alignment - fixed alignment.
	 * @param mapInfo   - square of the map displayed on the faction page.
	 */
	@Deprecated
	public static GOTFaction addFaction(String enumName, int color, GOTDimension dim, DimensionRegion region, boolean player, boolean registry, int alignment, GOTMapRegion mapInfo) {
		Class[] classArr = { Integer.TYPE, GOTDimension.class, DimensionRegion.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, GOTMapRegion.class };
		Object[] args = { color, dim, region, player, registry, alignment, mapInfo };
		return EnumHelper.addEnum(GOTFaction.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new faction invasion.
	 * @param enumName - name of the new invasion in enum.
	 * @param faction  - invasion belongs to this faction.
	 */
	public static GOTInvasions addInvasion(String enumName, GOTFaction faction) {
		return addInvasion(enumName, faction, null);
	}

	/**
	 * @apiNote Creates new secondary faction invasion
	 * @param enumName   - name of the new invasion in enum.
	 * @param faction    - invasion belongs to this faction.
	 * @param subfaction - invasion's name in lang file.
	 */
	public static GOTInvasions addInvasion(String enumName, GOTFaction faction, String subfaction) {
		Class[] classArr = { GOTFaction.class, String.class };
		Object[] args = { faction, subfaction };
		return EnumHelper.addEnum(GOTInvasions.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new lore category with its books.
	 * @param enumName - name of the new lore category in enum.
	 * @param name     - codename that is to be written in books.
	 */
	public static GOTLore.LoreCategory addLoreCategory(String enumName, String name) {
		Class[] classArr = { String.class };
		Object[] args = { name };
		return EnumHelper.addEnum(GOTLore.LoreCategory.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Adds lore to existing lore category.
	 * @param category - lore category.
	 * @param lore     - lore.
	 */
	public static void addLoreToLoreCategory(GOTLore.LoreCategory category, GOTLore lore) {
		category.loreList.add(lore);
	}

	/**
	 * @apiNote Creates new map label on the gui screen.
	 * @param enumName   - name of the new map label in enum.
	 * @param biomeLabel - name of this biome will be used in label.
	 * @param x          - coord of the pixel on map.png.
	 * @param y          - coord of the pixel on map.png.
	 * @param scale      - font size.
	 * @param angle      - rotation angle (hour hand).
	 * @param zoomMin    - label will be seen after this scale.
	 * @param zoomMax    - label will be seen before this scale.
	 */
	public static GOTMapLabels addMapLabel(String enumName, GOTBiome biomeLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return addMapLabel(enumName, (Object) biomeLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	private static GOTMapLabels addMapLabel(String enumName, Object label, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		Class[] classArr = { Object.class, Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Float.TYPE, Float.TYPE };
		Object[] args = { label, x, y, Float.valueOf(scale), angle, Float.valueOf(zoomMin), Float.valueOf(zoomMan) };
		return EnumHelper.addEnum(GOTMapLabels.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new map label on the gui screen.
	 * @param enumName    - name of the new map label in enum.
	 * @param stringLabel - name of the label for translation.
	 * @param x           - coord of the pixel on map.png.
	 * @param y           - coord of the pixel on map.png.
	 * @param scale       - font size.
	 * @param angle       - angle of the label on the map. 0 is horisontal.
	 * @param zoomMin     - label will be seen after this scale.
	 * @param zoomMax     - label will be seen before this scale.
	 */
	public static GOTMapLabels addMapLabel(String enumName, String stringLabel, int x, int y, float scale, int angle, float zoomMin, float zoomMan) {
		return addMapLabel(enumName, (Object) stringLabel, x, y, scale, angle, zoomMin, zoomMan);
	}

	/**
	 * @apiNote Creates new miniquest category.
	 * @param enumName - name of the new miniquest category in enum.
	 * @param name     - name of the subfolder.
	 */
	public static GOTMiniQuestFactory addMiniQuestFactory(String enumName, String name) {
		Class[] classArr = { String.class };
		Object[] args = { name };
		return EnumHelper.addEnum(GOTMiniQuestFactory.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new mountain.
	 * @param name - name of the new mountain in enum.
	 * @param x    - coord of the pixel on map.png.
	 * @param z    - coord of the pixel on map.png.
	 * @param h    - height.
	 * @param r    - radius.
	 */
	public static GOTMountains addMountain(String name, double x, double z, float h, int r) {
		return addMountain(name, x, z, h, r, 0);
	}

	/**
	 * @apiNote Creates new mountain.
	 * @param name - name of the new mountain in enum.
	 * @param x    - coord of the pixel on map.png.
	 * @param z    - coord of the pixel on map.png.
	 * @param h    - height.
	 * @param r    - radius.
	 * @param lava - lava crater radius. Usual mountain = 0.
	 */
	public static GOTMountains addMountain(String name, double x, double z, float h, int r, int lava) {
		Class[] classArr = { Double.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE };
		Object[] args = { x, z, Float.valueOf(h), r, lava };
		return EnumHelper.addEnum(GOTMountains.class, name, classArr, args);
	}

	/**
	 * @apiNote Creates new exclusive shield.
	 * @param enumName - name of the new shield in enum.
	 * @param hidden   - will be displayed in the GUI or not.
	 * @param players  - UUIDs of the owners.
	 */
	public static GOTShields addShield(String enumName, boolean hidden, List<String> players) {
		return addShield(enumName, GOTShields.ShieldType.EXCLUSIVE, hidden, players);
	}

	/**
	 * @apiNote Creates new exclusive shield.
	 * @param enumName - name of the new shield in enum.
	 * @param type     - exclusive/achievement/faction.
	 * @param hidden   - will be displayed in the GUI or not.
	 * @param players  - UUIDs of the owners.
	 */
	public static GOTShields addShield(String enumName, GOTShields.ShieldType type, boolean hidden, List<String> players) {
		Class[] classArr = { GOTShields.ShieldType.class, Boolean.TYPE, ArrayList.class };
		Object[] args = { type, hidden, players };
		return EnumHelper.addEnum(GOTShields.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new tree, that can be added in the biome.
	 * @param enumName    - name of the new tree type in enum.
	 * @param treeFactory - lambda constructions, see in GOTTreeType.java.
	 */
	public static GOTTreeType addTreeType(String enumName, Object treeFactory) {
		Class[] classArr = { GOTTreeType.ITreeFactory.class };
		Object[] args = { treeFactory };
		return EnumHelper.addEnum(GOTTreeType.class, enumName, classArr, args);
	}

	/**
	 * @apiNote Creates new waypoint.
	 * @param name    - name of the new waypoint in enum.
	 * @param region  - region of unlocking.
	 * @param faction - you should have 0+ rep with this faction to travel here.
	 * @param x       - coord of the pixel on map.png.
	 * @param z       - coord of the pixel on map.png.
	 * @param hidden  - hidden point is invisible before unlocking.
	 */
	public static GOTWaypoint addWaypoint(String name, GOTWaypoint.Region region, GOTFaction faction, double x, double z, boolean hidden) {
		Class[] classArr = { GOTWaypoint.Region.class, GOTFaction.class, Double.TYPE, Double.TYPE, Boolean.TYPE };
		Object[] args = { region, faction, x, z, hidden };
		return EnumHelper.addEnum(GOTWaypoint.class, name, classArr, args);
	}

	/**
	 * @apiNote Creates new waypoint.
	 * @param name    - name of the new waypoint in enum.
	 * @param region  - region of unlocking.
	 * @param faction - you should have 0+ rep with this faction to travel here.
	 * @param x       - coord of the pixel on map.png.
	 * @param z       - coord of the pixel on map.png.
	 */
	public static GOTWaypoint addWaypoint(String name, GOTWaypoint.Region region, GOTFaction faction, int x, int z) {
		return addWaypoint(name, region, faction, x, z, false);
	}

	/**
	 * @apiNote Creates new waypoint region for unlocking.
	 * @apiNote Don't forget to point out this region in needed biome.
	 * @apiNote Init regions BEGORE adding waypoints.
	 * @param name - name of the new waypoint region in enum.
	 */
	public static GOTWaypoint.Region addWaypointRegion(String name) {
		Class[] classArr = {};
		Object[] args = {};
		return EnumHelper.addEnum(GOTWaypoint.Region.class, name, classArr, args);
	}

	/**
	 * @apiNote Moves faction from one region category to the another.
	 * @param faction   - faction that will be moved.
	 * @param newRegion - new dimension region.
	 */
	public static void changeDimensionRegion(GOTFaction faction, DimensionRegion newRegion) {
		faction.factionRegion.factionList.remove(faction);
		newRegion.factionList.add(faction);
		faction.factionRegion = newRegion;
	}

	/**
	 * @apiNote Changes all the faction relations between factions to neutral.
	 */
	public static void clearAllRelations() {
		GOTFactionRelations.defaultMap.clear();
	}

	/**
	 * @apiNote Clears miniquest factory.
	 * @param factory - miniquest factory that will be cleared.
	 */
	public static void clearMiniQuestFactory(GOTMiniQuestFactory factory) {
		factory.baseSpeechGroup = null;
		factory.questAchievement = null;
		factory.loreCategories.clear();
		factory.alignmentRewardOverride = null;
		factory.noAlignRewardForEnemy = false;
		factory.questFactories.clear();
	}

	/**
	 * @apiNote Clears all the roads in the world.
	 * @apiNote Should be used at the FMLInitializationEvent or later.
	 */
	public static void clearRoadDataBase() {
		GOTRoads.allRoads.clear();
		GOTRoads.roadPointDatabase = new RoadPointDatabase();
	}

	/**
	 * @apiNote Clears all the walls in the world.
	 * @apiNote Should be used at the FMLInitializationEvent or later.
	 */
	public static void clearWallDataBase() {
		GOTWalls.allWalls.clear();
		GOTWalls.wallPointDatabase = new WallPointDatabase();
	}

	/**
	 * @apiNote GOT won't spam about the new update in the chat.
	 * @apiNote Should be used at the FMLPostInitializationEvent or later.
	 */
	public static void disableGOTUpdateChecker() {
		GOTVersionChecker.checkedUpdate = true;
	}

	private static <T, E> T findAndInvokeMethod(Object[] args, Class<? super E> clazz, E instance, String[] methodNames, Class<?>... methodTypes) {
		Method method = ReflectionHelper.findMethod(clazz, instance, methodNames, (Class[]) methodTypes);
		try {
			return (T) method.invoke(instance, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			GOTLog.logger.error("Error when getting method " + methodNames[0] + " from class " + clazz.getSimpleName());
			e.printStackTrace();
			return null;
		}
	}

	private static ModContainer getContainer(ResourceLocation res) {
		ModContainer modContainer = Loader.instance().getIndexedModList().get(res.getResourceDomain());
		if (modContainer == null) {
			throw new IllegalArgumentException("Can't find the mod container for the domain " + res.getResourceDomain());
		}
		return modContainer;
	}

	private static BufferedImage getImage(InputStream in) {
		try {
			return ImageIO.read(in);
		} catch (IOException e) {
			GOTLog.logger.error("Failed to convert a input stream into a buffered image.");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				GOTLog.logger.error("Failed to convert a input stream into a buffered image.");
			}
		}
		return null;
	}

	private static InputStream getInputStream(ModContainer container, String path) {
		return container.getClass().getResourceAsStream(path);
	}

	private static InputStream getInputStream(ResourceLocation res) {
		return getInputStream(getContainer(res), getPath(res));
	}

	/**
	 * @apiNote Returns the list of fields of needed type from class file.
	 * @param clazz - class file with fields.
	 * @param type  - needed type.
	 */
	public static <E, T> Set<T> getObjectFieldsOfType(Class<? extends E> clazz, Class<? extends T> type) {
		return getObjectFieldsOfType(clazz, null, type);
	}

	private static <E, T> HashSet<T> getObjectFieldsOfType(Class<? extends E> clazz, E instance, Class<? extends T> type) {
		HashSet<Object> list = new HashSet<>();
		try {
			for (Field field : clazz.getDeclaredFields()) {
				if (field == null) {
					continue;
				}
				Object fieldObj = null;
				if (Modifier.isStatic(field.getModifiers())) {
					fieldObj = field.get(null);
				} else if (instance != null) {
					fieldObj = field.get(instance);
				}
				if (fieldObj == null || !type.isAssignableFrom(fieldObj.getClass())) {
					continue;
				}
				list.add(fieldObj);
			}
		} catch (IllegalAccessException | IllegalArgumentException e) {
			GOTLog.logger.error("Errored when getting all field from: " + clazz.getName() + " of type: " + type.getName());
		}
		return (HashSet<T>) list;
	}

	private static String getPath(ResourceLocation res) {
		return "/assets/" + res.getResourceDomain() + "/" + res.getResourcePath();
	}

	@SideOnly(value = Side.CLIENT)
	private static ResourceLocation getTextureResourceLocation(InputStream in, String textureName) {
		BufferedImage img = getImage(in);
		if (img != null) {
			return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(textureName, new DynamicTexture(img));
		}
		return null;
	}

	/**
	 * @apiNote Removes blocks or items from the inventory.
	 * @param content - multiple blocks or items.
	 */
	public static void hideFromInventory(Object... content) {
		for (Object obj : content) {
			if (obj instanceof Block) {
				((Block) obj).setCreativeTab(null);
			} else if (obj instanceof Item) {
				((Item) obj).setCreativeTab(null);
			}
		}
	}

	/**
	 * @apiNote Removes selected capes from the GUI.
	 * @param content - multiple capes.
	 */
	public static void removeCapes(GOTCapes... content) {
		for (GOTCapes removal : content) {
			removal.isHidden = true;
		}
	}

	/**
	 * @apiNote Removes capes selected selected from the GUI.
	 * @param content - multiple capes.
	 */
	public static void removeCapesExcept(GOTCapes... content) {
		for (GOTCapes removal : GOTCapes.values()) {
			for (GOTCapes excluded : content) {
				if (excluded != removal) {
					removal.isHidden = true;
					break;
				}
			}
		}
	}

	private static void removeFaction(GOTFaction faction) {
		faction.allowPlayer = false;
		faction.hasFixedAlignment = true;
		faction.fixedAlignment = 0;
		if (faction.factionDimension != null) {
			faction.factionDimension.factionList.remove(faction);
		}
		if (faction.factionRegion != null) {
			faction.factionRegion.factionList.remove(faction);
		}
		faction.factionDimension = null;
		faction.factionRegion = null;
		faction.controlZones.clear();
	}

	/**
	 * @apiNote Removes selected factions from the GUI.
	 * @param content - multiple factions.
	 */
	public static void removeFactions(GOTFaction... content) {
		for (GOTFaction removal : content) {
			removeFaction(removal);
		}
	}

	/**
	 * @apiNote Removes factions except selected from the GUI.
	 * @param content - multiple factions.
	 */
	public static void removeFactionsExcept(GOTFaction... content) {
		for (GOTFaction removal : GOTFaction.values()) {
			for (GOTFaction excluded : content) {
				if (excluded != removal) {
					removeFaction(removal);
				}
			}
		}
	}

	private static void removeMapLabel(GOTMapLabels label) {
		label.maxZoom = 1.0E-4f;
		label.minZoom = 0;
	}

	/**
	 * @apiNote Removes selected map labels from the GUI.
	 * @param content - multiple map labels.
	 */
	public static void removeMapLabels(GOTMapLabels... content) {
		for (GOTMapLabels removal : content) {
			removeMapLabel(removal);
		}
	}

	/**
	 * @apiNote Removes map labels except selected from the GUI.
	 * @param content - multiple map labels.
	 */
	public static void removeMapLabelsExcept(GOTMapLabels... content) {
		for (GOTMapLabels removal : GOTMapLabels.values()) {
			for (GOTMapLabels excluded : content) {
				if (excluded != removal) {
					removeMapLabel(removal);
				}
			}
		}
	}

	/**
	 * @apiNote Removes selected shields from the GUI.
	 * @param content - multiple shields.
	 */
	public static void removeShields(GOTShields... content) {
		for (GOTShields removal : content) {
			removal.isHidden = true;
		}
	}

	/**
	 * @apiNote Removes shields except selected from the GUI.
	 * @param content - multiple shields.
	 */
	public static void removeShieldsExcept(GOTShields... content) {
		for (GOTShields removal : GOTShields.values()) {
			for (GOTShields excluded : content) {
				if (excluded != removal) {
					removal.isHidden = true;
					break;
				}
			}
		}
	}

	private static void removeTitle(GOTTitle title) {
		if (title.titleType == GOTTitle.TitleType.ACHIEVEMENT) {
			title.titleAchievement.achievementTitle = null;
			title.titleAchievement = null;
		}
		title.isHidden = true;
		GOTTitle.allTitles.remove(title);
	}

	/**
	 * @apiNote Removes selected titles from the GUI.
	 * @param content - multiple titles.
	 */
	public static void removeTitles(GOTTitle... content) {
		for (GOTTitle removal : content) {
			removeTitle(removal);
		}
	}

	/**
	 * @apiNote Removes titles except selected from the GUI.
	 * @param content - multiple titles.
	 */
	public static void removeTitlesExcept(GOTTitle... content) {
		for (GOTTitle removal : getObjectFieldsOfType(GOTTitle.class, GOTTitle.class)) {
			for (GOTTitle excluded : content) {
				if (excluded != removal) {
					removeTitle(removal);
				}
			}
		}
	}

	private static void removeWaypoint(GOTWaypoint wp) {
		wp.isHidden = true;
		wp.faction = GOTFaction.HOSTILE;
	}

	/**
	 * @apiNote Removes selected waypoints from the GUI.
	 * @param content - multiple waypoints.
	 */
	public static void removeWaypoints(GOTWaypoint... content) {
		for (GOTWaypoint removal : content) {
			removeWaypoint(removal);
		}
	}

	/**
	 * @apiNote Removes waypoints except selected from the GUI.
	 * @param content - multiple waypoints.
	 */
	public static void removeWaypointsExcept(GOTWaypoint... content) {
		for (GOTWaypoint removal : GOTWaypoint.values()) {
			for (GOTWaypoint excluded : content) {
				if (excluded != removal) {
					removeWaypoint(removal);
				}
			}
		}
	}

	/**
	 * @apiNote Changes map and sepia map in the GUI.
	 * @apiNote YourClientProxy should implement IResourceManagerReloadListener.
	 * @apiNote Add onInit() void to the YourClientProxy file, if doesn't exist.
	 * @apiNote Add next line into the onInit():
	 * @apiNote ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(new
	 *          YourClientProxy());
	 * @apiNote Add unimplemented onResourceManagerReload to the
	 *          YourClientProxy.
	 * @apiNote Use setClientMapImage void in the onResourceManagerReload.
	 * @param mapTexture - path to map file.
	 */
	@SideOnly(value = Side.CLIENT)
	public static void setClientMapImage(ResourceLocation mapTexture) {
		ResourceLocation sepiaMapTexture;
		GOTTextures.mapTexture = mapTexture;
		try {
			BufferedImage mapImage = getImage(Minecraft.getMinecraft().getResourceManager().getResource(mapTexture).getInputStream());
			sepiaMapTexture = GOTTextures.convertToSepia(mapImage, new ResourceLocation("got:map_sepia"));
		} catch (IOException e) {
			FMLLog.severe("Failed to generate GOT sepia map");
			e.printStackTrace();
			sepiaMapTexture = mapTexture;
		}
		GOTTextures.sepiaMapTexture = sepiaMapTexture;
	}

	/**
	 * @apiNote Changes region's dimension.
	 * @deprecated No sense: only GAME_OF_THRONES dimension is available.
	 * @param region    - dimension region.
	 * @param dimension - new region's dimension.
	 */
	@Deprecated
	public static void setDimensionForRegion(DimensionRegion region, GOTDimension dimension) {
		region.setDimension(dimension);
		dimension.dimensionRegions.add(region);
	}

	/**
	 * @apiNote Changes entity scale.
	 * @param entity - mob, that will be rescaled.
	 * @param width  - new width of the mob.
	 * @param height - new height of the mob.
	 */
	public static void setEntitySize(Entity entity, float width, float height) {
		findAndInvokeMethod(new Object[] { Float.valueOf(width), Float.valueOf(height) }, Entity.class, entity, new String[] { "setSize", "func_70105_a", "a" }, Float.TYPE, Float.TYPE);
	}

	/**
	 * @apiNote Changes map for the world generation.
	 * @apiNote Should be used at the FMLInitializationEvent or later.
	 * @param res - ResourceLocation with path to map file.
	 */
	public static void setServerMapImage(ResourceLocation res) {
		BufferedImage img = getImage(getInputStream(res));
		if (img != null) {
			GOTGenLayerWorld.imageWidth = img.getWidth();
			GOTGenLayerWorld.imageHeight = img.getHeight();
			int[] colors = img.getRGB(0, 0, GOTGenLayerWorld.imageWidth, GOTGenLayerWorld.imageHeight, null, 0, GOTGenLayerWorld.imageWidth);
			GOTGenLayerWorld.biomeImageData = new byte[GOTGenLayerWorld.imageWidth * GOTGenLayerWorld.imageHeight];
			for (int i = 0; i < colors.length; ++i) {
				int color = colors[i];
				Integer biomeID = GOTDimension.GAME_OF_THRONES.colorsToBiomeIDs.get(color);
				if (biomeID != null) {
					GOTGenLayerWorld.biomeImageData[i] = (byte) biomeID.intValue();
					continue;
				}
				GOTLog.logger.error("Found unknown biome on map: " + Integer.toHexString(color) + " at location: " + i % GOTGenLayerWorld.imageWidth + ", " + i / GOTGenLayerWorld.imageWidth);
				GOTGenLayerWorld.biomeImageData[i] = (byte) GOTBiome.ocean.biomeID;
			}
		}
	}
}