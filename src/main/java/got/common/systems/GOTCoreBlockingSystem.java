package got.common.systems;

import got.common.item.tool.GOTItemAxe;
import got.common.item.weapon.*;
import net.minecraft.item.ItemSword;

import java.util.HashMap;
import java.util.Map;

public class GOTCoreBlockingSystem {
    private static final Map<Class<?>, float[]> weaponBlockAngles = new HashMap<>();

    public static void registerWeapon(Class<?> weaponClass, float leftBlockAngle, float rightBlockAngle) {
        weaponBlockAngles.put(weaponClass, new float[]{leftBlockAngle, rightBlockAngle});
    }

    public static void registerWeapons() {
        registerWeapon(GOTItemSword.class, 35.0f, 35.0f);
        registerWeapon(ItemSword.class, 35.0f, 35.0f);
        registerWeapon(GOTItemHammer.class, 20.0f, 20.0f);
        registerWeapon(GOTItemPolearm.class, 25.0f, 25.0f);
        registerWeapon(GOTItemAxe.class, 28.0f, 28.0f);
        registerWeapon(GOTItemDagger.class, 20.0f, 20.0f);
        registerWeapon(GOTItemDornePolearm.class, 10.0f, 90.0f);
    }

    public static float[] getBlockAngles(Class<?> weaponClass) {
        return weaponBlockAngles.getOrDefault(weaponClass, new float[]{35.0f, 35.0f});
    }
}