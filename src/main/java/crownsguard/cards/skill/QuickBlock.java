package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.stances.TankStance;
import crownsguard.util.CardStats;

public class QuickBlock extends BaseCard {
    public static final String ID = makeID(QuickBlock.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 5;
    public QuickBlock() {
        super(ID, info,true);
        setBlock(BLOCK,UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new ChangeStanceAction(new TankStance()));
    }
}
