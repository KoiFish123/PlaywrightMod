package crownsguard.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToPowerCard;
import crownsguard.character.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class BreakfallAdept extends BaseCard implements ReactionToPowerCard {
    public static final String ID = makeID(BreakfallAdept.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -2
    );

    public BreakfallAdept(){
        super(ID,info);
        CardModifierManager.addModifier(this,new ReactionCardMod());
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public AbstractCard makeCopy() {
        return new BreakfallAdept();
    }


    @Override
    public void onPowerAppliedToPlayer(AbstractPower abstractPower, AbstractCreature p, AbstractCreature m) {
        // Empty Implementation
        if (abstractPower.ID.equals(VulnerablePower.POWER_ID)){
            addToTop(new DiscardSpecificCardAction(this));
            if (!upgraded)
                addToTop(new ReducePowerAction(player,player,VulnerablePower.POWER_ID,abstractPower.amount/2));
            else if (upgraded)
                addToTop(new ReducePowerAction(player,player,VulnerablePower.POWER_ID,abstractPower.amount));
        }
    }

    @Override
    public void onPowerAppliedToMonster(AbstractPower abstractPower, AbstractCreature m, AbstractCreature p) {
        // Empty Implementation
    }
}
