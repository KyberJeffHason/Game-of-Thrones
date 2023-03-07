package got.common.registers;

import got.common.potions.CombatlogPotion;
import net.minecraft.potion.Potion;

public class EffectRegister {

    public static int COMBATLOG_POTIONID = 101;

    public static void registerPotions() {
        CombatlogPotion combatlogPotion = new CombatlogPotion( COMBATLOG_POTIONID, false, 9388224);
        Potion.potionTypes[COMBATLOG_POTIONID] = combatlogPotion;
    }

}
