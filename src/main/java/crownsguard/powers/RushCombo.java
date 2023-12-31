package crownsguard.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.CrownsguardMod.makeID;


public class RushCombo extends BasePower {
    public static final String POWER_ID = makeID(RushCombo.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    public RushCombo(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfRound() {
        if (this.amount > 1)
        addToBot(new ReducePowerAction(this.owner, this.owner,this.ID,1));

        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        super.atEndOfRound();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (amount == 3){
            this.flash();
            addToBot(new GainEnergyAction(1));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
