package crownsguard.cards.reactionInterface;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface ReactionToPowerCard {

    default void onPowerAppliedToPlayer(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        // When power is apply to the player
    }

    default void onPowerAppliedToMonster(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        // When power is apply to a monster
    }
}
