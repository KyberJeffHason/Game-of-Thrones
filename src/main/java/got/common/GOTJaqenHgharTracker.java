package got.common;

import java.util.*;
import java.util.Map.Entry;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.Event;
import got.common.entity.essos.legendary.quest.GOTEntityJaqenHghar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GOTJaqenHgharTracker {
	public static Map<UUID, Integer> activeGreyWanderers = new HashMap<>();
	public static int greyWandererCooldown_MAX = 3600;
	public static int spawnInterval = 2400;
	public static int spawnCooldown;

	public static void addNewWanderer(UUID id) {
		activeGreyWanderers.put(id, 3600);
		GOTJaqenHgharTracker.markDirty();
	}

	public static boolean isWandererActive(UUID id) {
		return activeGreyWanderers.containsKey(id) && activeGreyWanderers.get(id) > 0;
	}

	public static void load(NBTTagCompound levelData) {
		activeGreyWanderers.clear();
		NBTTagList greyWandererTags = levelData.getTagList("GreyWanderers", 10);
		for (int i = 0; i < greyWandererTags.tagCount(); ++i) {
			NBTTagCompound nbt = greyWandererTags.getCompoundTagAt(i);
			try {
				UUID id = UUID.fromString(nbt.getString("ID"));
				int cd = nbt.getInteger("CD");
				activeGreyWanderers.put(id, cd);
			} catch (Exception e) {
				FMLLog.severe("Error loading GOT data: invalid Grey Wanderer");
				e.printStackTrace();
			}
		}
		if (levelData.hasKey("GWSpawnTick")) {
			spawnCooldown = levelData.getInteger("GWSpawnTick");
		} else {
			spawnCooldown = 2400;
		}
	}

	public static void markDirty() {
		GOTLevelData.markDirty();
	}

	public static void performSpawning(World world) {
		if (!activeGreyWanderers.isEmpty()) {
			return;
		}
		if (!world.playerEntities.isEmpty() && --spawnCooldown <= 0) {
			spawnCooldown = 2400;
			ArrayList<EntityPlayer> players = new ArrayList<>(world.playerEntities);
			Collections.shuffle(players);
			Random rand = world.rand;
			block0: for (EntityPlayer entityplayer : players) {
				if (!GOTLevelData.getData(entityplayer).hasAnyJHQuest()) {
					for (int attempts = 0; attempts < 32; ++attempts) {
						int k;
						float angle = rand.nextFloat() * 3.1415927f * 2.0f;
						int r = MathHelper.getRandomIntegerInRange(rand, 4, 16);
						int i = MathHelper.floor_double(entityplayer.posX + r * MathHelper.cos(angle));
						int j = world.getHeightValue(i, k = MathHelper.floor_double(entityplayer.posZ + r * MathHelper.sin(angle)));
						if (j > 62 && world.getBlock(i, j - 1, k).isOpaqueCube() && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j + 1, k).isNormalCube()) {
							GOTEntityJaqenHghar wanderer = new GOTEntityJaqenHghar(world);
							wanderer.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
							wanderer.liftSpawnRestrictions = true;
							wanderer.liftBannerRestrictions = true;
							Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(wanderer, world, (float) wanderer.posX, (float) wanderer.posY, (float) wanderer.posZ);
							if (canSpawn == Event.Result.ALLOW || canSpawn == Event.Result.DEFAULT && wanderer.getCanSpawnHere()) {
								wanderer.liftSpawnRestrictions = false;
								wanderer.liftBannerRestrictions = false;
								world.spawnEntityInWorld(wanderer);
								wanderer.onSpawnWithEgg(null);
								GOTJaqenHgharTracker.addNewWanderer(wanderer.getUniqueID());
								wanderer.arriveAt(entityplayer);
								break block0;
							}
						}
					}
				}
			}
		}

	}

	public static void save(NBTTagCompound levelData) {
		NBTTagList greyWandererTags = new NBTTagList();
		for (Map.Entry<UUID, Integer> e : activeGreyWanderers.entrySet()) {
			UUID id = e.getKey();
			int cd = e.getValue();
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("ID", id.toString());
			nbt.setInteger("CD", cd);
			greyWandererTags.appendTag(nbt);
		}
		levelData.setTag("GreyWanderers", greyWandererTags);
		levelData.setInteger("GWSpawnTick", spawnCooldown);
	}

	public static void setWandererActive(UUID id) {
		activeGreyWanderers.computeIfPresent(id, (key, value) -> {
			GOTJaqenHgharTracker.markDirty();
			return 3600;
		});
	}

	public static void updateCooldowns() {
		HashSet<UUID> removes = new HashSet<>();
		for (Entry<UUID, Integer> id : activeGreyWanderers.entrySet()) {
			int cd = id.getValue();
			cd--;
			activeGreyWanderers.put(id.getKey(), cd);
			if (cd <= 0) {
				removes.add(id.getKey());
			}
		}
		if (!removes.isEmpty()) {
			for (UUID id : removes) {
				activeGreyWanderers.remove(id);
			}
			GOTJaqenHgharTracker.markDirty();
		}
	}
}
