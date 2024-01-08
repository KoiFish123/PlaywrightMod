package crownsguard.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.cards.reactionInterface.ReactionToDrawCard;
import crownsguard.cards.reactionInterface.ReactionToExhaustCard;
import crownsguard.cards.reactionInterface.ReactionToPowerCard;
import crownsguard.character.crownsguard.TheCrownsguard;

import static crownsguard.CrownsguardMod.makeID;

public class WhiteSnakeWisdom extends BaseRelic{
    private static final String NAME = "WhiteSnakeWisdom"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.


    public WhiteSnakeWisdom() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard instanceof ReactionToDamageCard || targetCard instanceof ReactionToDrawCard || targetCard instanceof ReactionToExhaustCard || targetCard instanceof ReactionToPowerCard || targetCard instanceof ReactionToDrawCard)
        {
            flash();
            addToBot(new DrawCardAction(1));
        }
        super.onUseCard(targetCard, useCardAction);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
