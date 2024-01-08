package crownsguard.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

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

    @Override
    public void onEnterStance() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player, new DexterityPower(player,1),1));
    }

    @Override
    public void onExitStance() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player,player, new DexterityPower(player,-1),-1));
    }

    public TankStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }

}
