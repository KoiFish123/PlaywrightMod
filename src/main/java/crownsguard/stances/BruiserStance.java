package crownsguard.stances;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.stances.AbstractStance;

import static crownsguard.CrownsguardMod.makeID;

public class BruiserStance extends AbstractStance {
    public static final String STANCE_ID = makeID(BruiserStance.class.getSimpleName());

    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString("Bruiser");

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }

    public BruiserStance() {
        this.ID = STANCE_ID;
        this.name = stanceString.NAME;
        updateDescription();
    }
}
