package crownsguard.stances;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import crownsguard.character.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class TankStance extends AbstractStance{
    public static final String STANCE_ID = makeID(TankStance.class.getSimpleName());

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Tank");

    private boolean takenEffect = false;
    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    @Override
    public void atStartOfTurn() {
        takenEffect = false;
    }
//
//    @Override
//    public void onExitStance() {
//        ((TheCrownsguard)player).removePowerOnStanceExit(STANCE_ID);
//    }
//
//    @Override
//    public void onEnterStance() {
//        ((TheCrownsguard)player).gainPowerOnStanceEnter(STANCE_ID);
//    }

    public TankStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

}
