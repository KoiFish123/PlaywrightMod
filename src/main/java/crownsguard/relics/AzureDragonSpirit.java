package crownsguard.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;

public class AzureDragonSpirit extends BaseRelic{
    private static final String NAME = "AzureDragonSpirit"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final AbstractRelic.RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public AzureDragonSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void onEquip() {
        if (player instanceof PlaywrightCharacter) ((PlaywrightCharacter) player).maxEXCharge += 5;
        super.onEquip();
    }

    @Override
    public void onUnequip() {
        if (player instanceof PlaywrightCharacter) ((PlaywrightCharacter) player).maxEXCharge -= 5;
        super.onEquip();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
