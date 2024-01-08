package crownsguard.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.DrunkenMomentumPower;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class DrunkenMomentum extends BaseCard {
    public static final String ID = makeID(DrunkenMomentum.class.getSimpleName());


    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            2
    );

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public DrunkenMomentum() {
        super(ID, info,false);
        setInnate(false,true);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer, new DrunkenMomentumPower(abstractPlayer,1), 1));
    }

}
