package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.util.CardStats;

public class EXGuarding extends BaseCard {
    public static final String ID = makeID(EXGuarding.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );
    public EXGuarding() {
        super(ID, info,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof PlaywrightCharacter) addToBot(new GainBlockAction(p,((PlaywrightCharacter) p).exCharge));
    }

    @Override
    public void triggerExhaustedCardsOnStanceChange(AbstractStance newStance) {
        if (upgraded) addToBot(new DiscardToHandAction(this));
    }
}
