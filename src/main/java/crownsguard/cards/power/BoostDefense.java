package crownsguard.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.util.CardStats;


public class BoostDefense extends BaseCard {
    public static final String ID = makeID(BoostDefense.class.getSimpleName());


    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.POWER,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public BoostDefense() {
        super(ID, info,false);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        updateNameAndDesc();
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded < 3) {
            ++this.timesUpgraded;
            this.upgradeMagicNumber((1));
            this.upgraded = true;
            updateNameAndDesc();
        }
    }

    public void updateNameAndDesc() {
        this.name = cardStrings.NAME + " Lv." + (this.timesUpgraded+1);
        this.initializeTitle();
        this.rawDescription = cardStrings.DESCRIPTION;
        if (timesUpgraded != 3)
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[this.timesUpgraded];
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction((AbstractCreature)abstractPlayer, (AbstractCreature)abstractPlayer, (AbstractPower)new DexterityPower((AbstractCreature)abstractPlayer, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BoostDefense();
    }
}
