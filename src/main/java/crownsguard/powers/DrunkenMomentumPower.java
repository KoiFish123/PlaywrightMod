package crownsguard.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.CrownsguardMod.makeID;

public class DrunkenMomentumPower extends BasePower{
    public static final String POWER_ID = makeID(DrunkenMomentumPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public DrunkenMomentumPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        if (owner.hasPower(DrunkPower.POWER_ID)) addToBot(new GainEnergyAction(owner.getPower(DrunkPower.POWER_ID).amount/3));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
