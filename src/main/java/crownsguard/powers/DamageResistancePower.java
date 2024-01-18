package crownsguard.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.PlaywrightMod.makeID;

public class DamageResistancePower extends BasePower{
    public static final String POWER_ID = makeID(DamageResistancePower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public DamageResistancePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (amount > 0) {
            if (type != DamageInfo.DamageType.HP_LOSS)
                return damage/2;
        }
        return super.atDamageFinalReceive(damage, type);
    }

//    @Override
//    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        // Use this to apply the other damage type
//        super.onApplyPower(power, target, source);
//    }
}
