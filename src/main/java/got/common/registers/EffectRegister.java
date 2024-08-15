package got.common.registers;

import got.common.potions.BleedingPotion;
import got.common.potions.CombatlogPotion;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class EffectRegister {

    public static Potion bleeding;
    private static int idCounter = 102;
    public static int COMBATLOG_POTIONID = 101;

    public static void registerPotions() {
        CombatlogPotion combatlogPotion = new CombatlogPotion( COMBATLOG_POTIONID, false, 9388224);
        Potion.potionTypes[COMBATLOG_POTIONID] = combatlogPotion;

        bleeding = new BleedingPotion(idCounter, true, 8171463, new ResourceLocation("got", "textures/potions/bleeding.png"), "got.potion.bleeding");
        registerPotion(bleeding);

    }

    private static void registerPotion(Potion pot) {
        Potion.potionTypes[idCounter] = pot;
        idCounter++;
    }
}
