package crownsguard.cards.reactionInterface;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public interface ReactionToPowerCard {

    void onPowerAppliedToPlayer(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source);

    void onPowerAppliedToMonster(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source);
}
