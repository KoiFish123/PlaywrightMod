package crownsguard.cards.status;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.powers.DrunkPower;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Smashed extends BaseCard {
    public static final String ID = makeID(Smashed.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.COMMON,
            CardTarget.NONE,
            -2
    );

    public Smashed() {
        super(ID, info,true);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Empty Implementation
    }


    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        if (player.hasPower(DrunkPower.POWER_ID)){
            int drunkAmount = player.getPower(DrunkPower.POWER_ID).amount;
            addToTop(new ReducePowerAction(player,player,DrunkPower.POWER_ID,drunkAmount/2));
            if (drunkAmount/2 > 1)
                addToTop(new LoseEnergyAction(1));
        }
    }
}
