package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoBlockPower;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.DamageResistance;
import crownsguard.powers.TempDamageResistance;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


public class ResistGuard extends BaseCard {
    public static final String ID = makeID(ResistGuard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        addToBot(new ApplyPowerAction(p,p,new TempDamageResistance(p,1)));
        addToBot(new RemoveAllBlockAction(p,p));
        addToBot(new ApplyPowerAction(p,p,new NoBlockPower(p,1,true)));
    }

    @Override
    public void triggerAtStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(player,player,DamageResistance.POWER_ID));
    }

    public ResistGuard() {
        super(ID, info,false);
        setSelfRetain(false,true);
    }
}
