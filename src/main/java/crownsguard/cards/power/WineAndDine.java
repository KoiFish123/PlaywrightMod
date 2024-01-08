package crownsguard.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.WineAndDinePower;
import crownsguard.util.CardStats;

public class WineAndDine extends BaseCard {
    public static final String ID = makeID(WineAndDine.class.getSimpleName());


    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.POWER,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public WineAndDine() {
        super(ID, info,true);
        setInnate(false,true);
        setCostUpgrade(0);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer, new WineAndDinePower(abstractPlayer,1), 1));
    }
}
