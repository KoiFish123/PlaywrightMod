package crownsguard.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.CrownsguardMod.makeID;

public class DamageResistance extends BasePower{
    public static final String POWER_ID = makeID(DamageResistance.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public DamageResistance(AbstractCreature owner,int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (amount > 0) {
            if (info.type != DamageInfo.DamageType.HP_LOSS)
                return super.onAttacked(info, damageAmount / 2);
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (amount == 0) amount++;
    }
}
