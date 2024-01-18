package crownsguard.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.PlaywrightMod.makeID;

public class TempDamageResistancePower extends BasePower{
    public static final String POWER_ID = makeID(TempDamageResistancePower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = true;

    public TempDamageResistancePower(AbstractCreature owner, int turn) {
        super(POWER_ID, TYPE, TURN_BASED, owner, turn);
        updateDescription();
        this.description = DESCRIPTIONS[0] + turn + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.HP_LOSS)
            return super.atDamageFinalReceive(damage/2, type);


        return super.atDamageFinalReceive(damage, type);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ReducePowerAction(this.owner, this.owner, TempDamageResistancePower.POWER_ID, 1));
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, TempDamageResistancePower.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
