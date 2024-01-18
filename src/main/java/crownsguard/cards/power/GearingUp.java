package crownsguard.cards.power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.GearingUpPower;
import crownsguard.util.CardStats;

public class GearingUp extends BaseCard {

    public static final String ID = makeID(GearingUp.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.POWER,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            2
    );

    private static final int BLOCK_GAIN = 3;

    public GearingUp() {
        super(ID, info,false);
        setMagic(BLOCK_GAIN);
        setCostUpgrade(1);
    }
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new GearingUpPower(abstractPlayer, this.magicNumber), this.magicNumber));
    }
}
