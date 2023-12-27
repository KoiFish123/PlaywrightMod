package crownsguard.cards.reactionInterface;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface ReactionToDamageCard {
    int onPlayerDamaged(int amount, DamageInfo info);

}
