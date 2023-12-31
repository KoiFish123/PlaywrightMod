package crownsguard.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import crownsguard.cards.BaseCard;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.TheCrownsguard;
import crownsguard.stances.BruiserStance;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Quickstep extends BaseCard implements ReactionToDamageCard {
    public static final String ID = makeID(Quickstep.class.getSimpleName());

    private static final int EVADE_POWER = 6;
    private static final int UPG_EVADE_POWER = 3;
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            -2
    );

    public Quickstep() {
        super(ID, info,true);
        CardModifierManager.addModifier(this, new ReactionCardMod());
        setSelfRetain(true);
        this.baseMagicNumber = EVADE_POWER;
        setBlock(EVADE_POWER, UPG_EVADE_POWER);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {
        if (amount <= block) {
            addToTop(new DiscardSpecificCardAction(this));
            if (upgraded)
                addToTop(new DrawPileToHandAction(1, CardType.ATTACK));
            return 0;
        }
        return amount;
    }
}
