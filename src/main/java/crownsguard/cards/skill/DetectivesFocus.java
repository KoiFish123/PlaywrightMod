package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class DetectivesFocus extends BaseCard {
    public static final String ID = makeID(DetectivesFocus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 2;
    public DetectivesFocus() {
        super(ID, info,false);
        setBlock(BLOCK,UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (player.isBloodied)
            addToBot(new DiscardToHandAction(this));
        super.triggerOnEndOfPlayerTurn();
    }
}
