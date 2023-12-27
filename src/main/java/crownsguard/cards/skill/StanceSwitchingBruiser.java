package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.TheCrownsguard;
import crownsguard.stances.BruiserStance;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class StanceSwitchingBruiser extends BaseCard {
    public static final String ID = makeID(StanceSwitchingBruiser.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.SKILL,
            CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ChangeStanceAction(new BruiserStance()));
    }

    public StanceSwitchingBruiser() {
        super(ID, info,true);
        setSelfRetain(true);
        setExhaust(true);
        this.costUpgrade = 1;
    }
}
