package crownsguard.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import crownsguard.character.crownsguard.TheCrownsguard;

import static crownsguard.CrownsguardMod.makeID;

public class YellowDragonSpirit extends BaseRelic{
    private static final String NAME = "YellowDragonSpirit";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE;
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL;

    public YellowDragonSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
