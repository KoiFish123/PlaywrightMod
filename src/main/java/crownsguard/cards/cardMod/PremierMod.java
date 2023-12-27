package crownsguard.cards.cardMod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


/*
Card mod: Playing any other cards will discard this one.
I called it Premier because it has to be the first one you play
 */
public class PremierMod extends AbstractCardModifier{

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(card));
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PremierMod();
    }
}
