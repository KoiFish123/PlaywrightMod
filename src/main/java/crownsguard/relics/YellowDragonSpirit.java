package crownsguard.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class YellowDragonSpirit extends BaseRelic{
    private static final String NAME = "YellowDragonSpirit"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public YellowDragonSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }
}
