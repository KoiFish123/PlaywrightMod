package crownsguard.relics;

import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;

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

    private boolean isActive = false;

    @Override
    public void atTurnStart() {
        if (player instanceof PlaywrightCharacter && ((PlaywrightCharacter) player).exCharge < 10 && player.isBloodied){
            this.flash();
            ((PlaywrightCharacter) player).increaseEXCharge(1);
        }
    }

    @Override
    public void onBloodied() {
        this.flash();
        this.beginLongPulse();
        super.onBloodied();
    }

    @Override
    public void onNotBloodied() {
        this.stopPulse();;
        super.onNotBloodied();
    }

    public void onVictory() {
        this.stopPulse();;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
