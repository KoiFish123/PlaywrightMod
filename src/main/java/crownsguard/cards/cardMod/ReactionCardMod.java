package crownsguard.cards.cardMod;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
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

    private int reactionTimer;

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    public void onRetained(AbstractCard card) {
        if (reactionTimer > 0)
            reactionTimer--;

        if (reactionTimer == 0){
            card.selfRetain = false;
        }
    }

    @Override
    public Color getGlow(AbstractCard card) {
        if (this.reactionTimer == 0)
            return Color.RED;
        if (this.reactionTimer == 1)
            return Color.YELLOW;
        if (this.reactionTimer > 1)
            return Color.GREEN;
        return super.getGlow(card);
    }

    @Override
    public void onDrawn(AbstractCard card) {
        this.reactionTimer = 1;
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
