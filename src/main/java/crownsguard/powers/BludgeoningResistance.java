package crownsguard.powers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.damage.BludgeoningDamage;
import crownsguard.damage.HeatActionDamage;


import static crownsguard.CrownsguardMod.makeID;

public class BludgeoningResistance extends BasePower {
    public static final String POWER_ID = makeID(BludgeoningResistance.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public BludgeoningResistance(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
            if (mod instanceof BludgeoningDamage) {
                damageAmount = damageAmount/2;
            }
        }
        return damageAmount;
    }
}
