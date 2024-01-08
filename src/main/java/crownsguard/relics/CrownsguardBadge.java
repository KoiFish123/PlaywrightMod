package crownsguard.relics;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.stances.BruiserStance;
import crownsguard.stances.TankStance;

import java.util.Random;

import static crownsguard.CrownsguardMod.makeID;

public class CrownsguardBadge extends BaseRelic {
    private static final String NAME = "CrownsguardBadge";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public CrownsguardBadge() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    Random rand = new Random();

    @Override
    public void atBattleStartPreDraw() {

        this.flash();
        int randomStance = AbstractDungeon.miscRng.random(0, 1);

        switch (randomStance) {
            case 0:
                addToBot(new ChangeStanceAction(new TankStance()));
                break;
            case 1:
                addToBot(new ChangeStanceAction(new BruiserStance()));
                break;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
