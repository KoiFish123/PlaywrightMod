package crownsguard.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.CollateralDamagePower;
import crownsguard.util.CardStats;

public class ALittleCollateralDamage extends BaseCard {
    public static final String ID = makeID(ALittleCollateralDamage.class.getSimpleName());

    public int DAMAGE = 1;
    public int UPG_DAMAGE = 1;

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.POWER,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public ALittleCollateralDamage() {
        super(ID, info,false);
        this.baseMagicNumber = DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer, new CollateralDamagePower(abstractPlayer,magicNumber), this.magicNumber));
    }
}
