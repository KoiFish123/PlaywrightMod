package crownsguard.powers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.damage.mainDamage.BluntDamage;


import static crownsguard.PlaywrightMod.makeID;

public class BludgeoningResistancePower extends BasePower {
    public static final String POWER_ID = makeID(BludgeoningResistancePower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public BludgeoningResistancePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
            if (mod instanceof BluntDamage) {
                damageAmount = damageAmount/2;
            }
        }
        return damageAmount;
    }
}
