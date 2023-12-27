package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.stances.TankStance;
import crownsguard.character.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class StanceSwitchingTank extends BaseCard {
    public static final String ID = makeID(StanceSwitchingTank.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
            addToBot(new ChangeStanceAction(new TankStance()));
    }

    public StanceSwitchingTank() {
        super(ID, info,true);
        setSelfRetain(true);
        setExhaust(true);
        this.costUpgrade = 1;
    }
}
