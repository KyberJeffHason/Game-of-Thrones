package got.common.systems;

import got.common.item.weapon.GOTItemBattleaxe;

import java.util.HashMap;
import java.util.Map;

public class GOTCoreBlockingSystem {
    private static final Map<Class<?>, float[]> weaponBlockAngles = new HashMap<>();

    public static void registerWeapon(Class<?> weaponClass, float leftBlockAngle, float rightBlockAngle) {
        weaponBlockAngles.put(weaponClass, new float[]{leftBlockAngle, rightBlockAngle});
    }

    public static void registerWeapons() {
        registerWeapon(GOTItemBattleaxe.class, 10.0f, 90.0f);
    }

    public static float[] getBlockAngles(Class<?> weaponClass) {
        return weaponBlockAngles.getOrDefault(weaponClass, new float[]{45.0f, 45.0f});
    }
}