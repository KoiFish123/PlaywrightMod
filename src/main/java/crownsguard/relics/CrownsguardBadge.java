package crownsguard.relics;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crownsguard.character.TheCrownsguard;
import crownsguard.stances.BruiserStance;
import crownsguard.stances.TankStance;

import java.util.Random;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class CrownsguardBadge extends BaseRelic {
    private static final String NAME = "CrownsguardBadge"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public CrownsguardBadge() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    Random rand = new Random();

    @Override
    public void atBattleStartPreDraw() {
        // Generate a random integer between 0 and 1
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
}
