package crownsguard.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import crownsguard.character.PlaywrightCharacter;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class ExtremeHeatMode extends BasePower{
    public static final String POWER_ID = makeID(ExtremeHeatMode.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    private int heatOnActivation;

    private int bonus;

    private int heatLossOverTurn;


    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) damage += bonus;

        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        blockAmount += bonus;
        return blockAmount;
    }

    @Override
    public void atStartOfTurn() {
        if (player instanceof PlaywrightCharacter) {
            if (((PlaywrightCharacter) player).heat > 0) {
                addToBot(new GainEnergyAction(1));
                addToBot(new DrawCardAction(1));
            } else
                addToBot(new RemoveSpecificPowerAction(player, player,this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            if (player instanceof PlaywrightCharacter) {
                ((PlaywrightCharacter)player).decreaseHeat(heatLossOverTurn);
            }
        }
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (((PlaywrightCharacter) player).heat == 0){
            addToBot(new RemoveSpecificPowerAction(player, player,this));
        }
    }


    @Override
    public void onRemove() {
        ((PlaywrightCharacter)player).updateHeatAfterExitExtremeHeat(heatOnActivation);
        super.onRemove();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.bonus + DESCRIPTIONS[1] + this.bonus + DESCRIPTIONS[2] + heatLossOverTurn + DESCRIPTIONS[3];
    }

    public ExtremeHeatMode(AbstractCreature owner, int heatLossOverTurn) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        if (owner instanceof PlaywrightCharacter) heatOnActivation = ((PlaywrightCharacter) owner).heat;

        this.bonus = heatOnActivation/5;

        this.heatLossOverTurn = heatLossOverTurn;

        updateDescription();
    }
}
