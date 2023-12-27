package crownsguard.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class VermillionBirdSpirit extends BaseRelic{
    private static final String NAME = "VermillionBirdSpirit"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public VermillionBirdSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        if (player instanceof PlaywrightCharacter && ((PlaywrightCharacter) player).heat < 10 && player.isBloodied)
            ((PlaywrightCharacter) player).increaseHeat(1);
    }
}
