package crownsguard.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static crownsguard.CrownsguardMod.makeID;

public class PlaywrightFlightPower extends BasePower {
    public static final String POWER_ID = makeID(PlaywrightFlightPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = true;
    public PlaywrightFlightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount/2 >= AbstractDungeon.player.currentBlock){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, this,1));
        }
        return damageAmount/2;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this,1));
    }
}
