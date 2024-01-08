package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.ChargedPower;
import crownsguard.util.CardStats;

public class Charge extends BaseCard {
    public static final String ID = makeID(Charge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Charge() {
        super(ID, info,true);
        setEthereal(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ChargedPower(p)));
    }
}
