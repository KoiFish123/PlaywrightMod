package crownsguard.cards.cardMod;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/*
- Reaction card's effect will trigger when the conditions on it are met.
- Then it will discard itself (some will exhaust itself instead)
- Reaction card will retain in hand for one turn (I am considering a relic that can increase the window)
- Playing a reaction card will let you draw another card
 */
public class ReactionCardMod extends AbstractCardModifier {

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
//        if (player.hasRelic(WhiteSnakeWisdom.ID))
//            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        /*
        TODO: White Snake Wisdom
         - Create the relic WhiteSnakeWisdom
         - Add a check to the relic to see if the card play is a reaction to pulse() it
         */
    }
    public void onRetained(AbstractCard card) {
        card.selfRetain = false;
    }

    @Override
    public void onDrawn(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ReactionCardMod();
    }
}
