package got.common.item;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import got.common.enchant.GOTEnchantmentHelper;
import got.common.item.weapon.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.common.ISpecialArmor;

public class GOTWeaponStats {
	public static int basePlayerMeleeTime = 15;
	public static int baseMobMeleeTime = 20;
	public static Map<Object, Float> meleeSpeed = new HashMap<>();
	public static Map<Object, Float> meleeReach = new HashMap<>();
	public static Map<Object, Integer> meleeExtraKnockback = new HashMap<>();
	public static float MAX_MODIFIABLE_REACH = 2.0f;
	public static float MAX_MODIFIABLE_SPEED = 1.6f;
	public static int MAX_MODIFIABLE_KNOCKBACK = 2;

	static {
		GOTWeaponStats.registerMeleeSpeed(GOTItemGreatsword.class, 0.667f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemLongsword.class, 0.8f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemSword.class, 0.6897f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemBattleaxe.class, 0.60f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemArrynClaymore.class, 0.629f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemRiverlandsTrident.class, 0.48f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemIronBornAxe.class, 0.545f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemReachPike.class, 0.30f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemStormlandsHammer.class, 0.3f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemDornePolearm.class, 0.54f);
		GOTWeaponStats.registerMeleeReach(GOTItemArrynClaymore.class, 1.04f);
		GOTWeaponStats.registerMeleeReach(GOTItemRiverlandsTrident.class, 1.9f);
		GOTWeaponStats.registerMeleeReach(GOTItemIronBornAxe.class, 1.04f);
		GOTWeaponStats.registerMeleeReach(GOTItemReachPike.class, 2.2f);
		GOTWeaponStats.registerMeleeReach(GOTItemStormlandsHammer.class, 1.15f);
		GOTWeaponStats.registerMeleeReach(GOTItemDornePolearm.class, 1.45f);
		GOTWeaponStats.registerMeleeReach(GOTItemGreatsword.class, 1.5f);
		GOTWeaponStats.registerMeleeReach(GOTItemLongsword.class, 1.25f);
		GOTWeaponStats.registerMeleeReach(GOTItemSword.class, 0.957f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemDagger.class, 1.5f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemSpear.class, 0.65f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemPolearm.class, 0.667f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemPolearmLong.class, 0.38f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemLance.class, 0.5f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemHammer.class, 0.45f);
		GOTWeaponStats.registerMeleeReach(GOTItemDagger.class, 0.75f);
		GOTWeaponStats.registerMeleeReach(GOTItemSpear.class, 1.3f);
		GOTWeaponStats.registerMeleeReach(GOTItemPolearm.class, 1.5f);
		GOTWeaponStats.registerMeleeReach(GOTItemPolearmLong.class, 2.0f);
		GOTWeaponStats.registerMeleeReach(GOTItemLance.class, 2.0f);
		GOTWeaponStats.registerMeleeReach(GOTItemHammer.class, 1.0f);
		GOTWeaponStats.registerMeleeExtraKnockback(GOTItemHammer.class, 1);
		GOTWeaponStats.registerMeleeExtraKnockback(GOTItemLance.class, 1);
		GOTWeaponStats.registerMeleeReach(GOTItemNorthGreatSword.class, 1.1f);
		GOTWeaponStats.registerMeleeSpeed(GOTItemNorthGreatSword.class,0.545f);
	}

	public static int getArmorProtection(ItemStack itemstack) {
		Item item;
		if (itemstack != null && (item = itemstack.getItem()) instanceof ItemArmor) {
			ItemArmor armor = (ItemArmor) item;
			int i = armor.damageReduceAmount;
			return i += GOTEnchantmentHelper.calcCommonArmorProtection(itemstack);
		}
		return 0;
	}

	public static int getAttackTimeMob(ItemStack itemstack) {
		return GOTWeaponStats.getAttackTimeWithBase(itemstack, baseMobMeleeTime);
	}

	public static int getAttackTimePlayer(ItemStack itemstack) {
		return GOTWeaponStats.getAttackTimeWithBase(itemstack, basePlayerMeleeTime);
	}

	public static int getAttackTimeWithBase(ItemStack itemstack, int baseTime) {
		float time = baseTime;
		Float factor = (Float) GOTWeaponStats.getClassOrItemProperty(itemstack, meleeSpeed);
		if (factor != null) {
			time /= factor;
		}
		time /= GOTEnchantmentHelper.calcMeleeSpeedFactor(itemstack);
		time = Math.max(time, 1.0f);
		return Math.round(time);
	}

	public static int getBaseExtraKnockback(ItemStack itemstack) {
		int kb = 0;
		Integer extra = (Integer) GOTWeaponStats.getClassOrItemProperty(itemstack, meleeExtraKnockback);
		if (extra != null) {
			kb = extra;
		}
		return kb;
	}

	public static Object getClassOrItemProperty(ItemStack itemstack, Map propertyMap) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (propertyMap.containsKey(item)) {
				return propertyMap.get(item);
			}
			Class<?> itemClass = item.getClass();
			do {
				if (propertyMap.containsKey(itemClass)) {
					return propertyMap.get(itemClass);
				}
				if (itemClass == Item.class) {
					break;
				}
				itemClass = itemClass.getSuperclass();
			} while (true);
		}
		return null;
	}

	public static float getMeleeDamageBonus(ItemStack itemstack) {
		Multimap weaponAttributes;
		float damage = 0.0f;
		if (itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers()) != null) {
			for (Object obj : weaponAttributes.entries()) {
				Entry e = (Entry) obj;
				AttributeModifier mod = (AttributeModifier) e.getValue();
				if (mod.getID() != GOTItemSword.accessWeaponDamageModifier()) {
					continue;
				}
				damage = (float) (damage + mod.getAmount());
				damage += EnchantmentHelper.func_152377_a(itemstack, EnumCreatureAttribute.UNDEFINED);
			}
		}
		return damage;
	}

	public static float getMeleeExtraLookWidth() {
		return 1.0f;
	}

	public static float getMeleeReachDistance(EntityPlayer entityplayer) {
		float reach = 3.0f;
		reach *= GOTWeaponStats.getMeleeReachFactor(entityplayer.getHeldItem());
		if (entityplayer.capabilities.isCreativeMode) {
			reach = (float) (reach + 3.0);
		}
		return reach;
	}

	public static float getMeleeReachFactor(ItemStack itemstack) {
		float reach = 1.0f;
		Float factor = (Float) GOTWeaponStats.getClassOrItemProperty(itemstack, meleeReach);
		if (factor != null) {
			reach *= factor;
		}
		return reach *= GOTEnchantmentHelper.calcMeleeReachFactor(itemstack);
	}

	public static float getMeleeSpeed(ItemStack itemstack) {
		int base = basePlayerMeleeTime;
		return 1.0f / ((float) GOTWeaponStats.getAttackTimeWithBase(itemstack, base) / (float) base);
	}

	public static float getRangedDamageFactor(ItemStack itemstack, boolean launchSpeedOnly) {
		float baseArrowFactor = 2.0f;
		float weaponFactor = 0.0f;
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemCrossbow) {
				weaponFactor = baseArrowFactor * (float) ((GOTItemCrossbow) item).boltDamageFactor;
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
				if (!launchSpeedOnly) {
					int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);
					if (power > 0) {
						weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
					}
					weaponFactor *= 2.0f;
				}
			} else if (item instanceof ItemBow) {
				int power;
				weaponFactor = baseArrowFactor;
				if (item instanceof GOTItemBow) {
					weaponFactor *= (float) ((GOTItemBow) item).arrowDamageFactor;
				}
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
				if (!launchSpeedOnly && (power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack)) > 0) {
					weaponFactor = (float) (weaponFactor + (power * 0.5 + 0.5));
				}
			} else if (item instanceof GOTItemSarbacane) {
				weaponFactor = baseArrowFactor;
				if (!launchSpeedOnly) {
					weaponFactor *= 1.0f / baseArrowFactor;
				}
				weaponFactor *= GOTEnchantmentHelper.calcRangedDamageFactor(itemstack);
			} else if (item instanceof GOTItemSpear) {
				weaponFactor = ((GOTItemSpear) item).getRangedDamageMultiplier(itemstack, null, null);
			} else if (item instanceof GOTItemThrowingAxe) {
				weaponFactor = ((GOTItemThrowingAxe) item).getRangedDamageMultiplier(itemstack, null, null);
			}
		}
		if (weaponFactor > 0.0f) {
			return weaponFactor / baseArrowFactor;
		}
		return 0.0f;
	}

	public static int getRangedKnockback(ItemStack itemstack) {
		if (GOTWeaponStats.isMeleeWeapon(itemstack) || itemstack != null && itemstack.getItem() instanceof GOTItemThrowingAxe) {
			return GOTWeaponStats.getTotalKnockback(itemstack);
		}
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack) + GOTEnchantmentHelper.calcRangedKnockback(itemstack);
	}

	public static float getRangedSpeed(ItemStack itemstack) {
		int base = 20;
		int time = 0;
		if (itemstack != null) {
			Item item = itemstack.getItem();
			if (item instanceof GOTItemCrossbow) {
				time = ((GOTItemCrossbow) item).getMaxDrawTime();
			} else if (item instanceof GOTItemBow) {
				time = ((GOTItemBow) item).getMaxDrawTime();
			} else if (item == Items.bow) {
				time = 20;
			}
			if (item instanceof GOTItemSpear) {
				time = ((GOTItemSpear) item).getMaxDrawTime();
			}
			if (item instanceof GOTItemSarbacane) {
				time = ((GOTItemSarbacane) item).getMaxDrawTime();
			}
		}
		if (time > 0) {
			return 1.0f / ((float) time / (float) base);
		}
		return 0.0f;
	}

	public static int getTotalArmorValue(EntityPlayer entityplayer) {
		int protection = 0;
		for (int i = 0; i < entityplayer.inventory.armorInventory.length; ++i) {
			ItemStack stack = entityplayer.inventory.armorInventory[i];
			if (stack != null && stack.getItem() instanceof ISpecialArmor) {
				protection += ((ISpecialArmor) stack.getItem()).getArmorDisplay(entityplayer, stack, i);
				continue;
			}
			if (stack == null || !(stack.getItem() instanceof ItemArmor)) {
				continue;
			}
			protection += GOTWeaponStats.getArmorProtection(stack);
		}
		return protection;
	}

	public static int getTotalKnockback(ItemStack itemstack) {
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemstack) + GOTWeaponStats.getBaseExtraKnockback(itemstack) + GOTEnchantmentHelper.calcExtraKnockback(itemstack);
	}

	public static boolean isMeleeWeapon(ItemStack itemstack) {
		Multimap weaponAttributes;
		if (itemstack != null && (weaponAttributes = itemstack.getAttributeModifiers()) != null) {
			for (Object obj : weaponAttributes.entries()) {
				Entry e = (Entry) obj;
				AttributeModifier mod = (AttributeModifier) e.getValue();
				if (mod.getID() != GOTItemSword.accessWeaponDamageModifier()) {
					continue;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean isPoisoned(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return item instanceof GOTItemDagger && ((GOTItemDagger) item).getHitEffect() == GOTItemSword.HitEffect.POISON;
		}
		return false;
	}

	public static boolean isRangedWeapon(ItemStack itemstack) {
		if (itemstack != null) {
			Item item = itemstack.getItem();
			return item instanceof ItemBow || item instanceof GOTItemSpear || item instanceof GOTItemSarbacane || item instanceof GOTItemThrowingAxe;
		}
		return false;
	}

	public static void registerMeleeExtraKnockback(Object obj, int i) {
		meleeExtraKnockback.put(obj, i);
	}

	public static void registerMeleeReach(Object obj, float f) {
		meleeReach.put(obj, f);
	}

	public static void registerMeleeSpeed(Object obj, float f) {
		meleeSpeed.put(obj, f);
	}
}
