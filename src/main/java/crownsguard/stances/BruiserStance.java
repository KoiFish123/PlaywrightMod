package crownsguard.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class BruiserStance extends AbstractStance {
    public static final String STANCE_ID = makeID(BruiserStance.class.getSimpleName());

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Bruiser");

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    @Override
    public void onEnterStance() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player, new StrengthPower(player,1),1));
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player, new StrengthPower(player,-1),-1));
    }

    public BruiserStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }
}
