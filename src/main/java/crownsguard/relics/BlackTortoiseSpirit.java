package crownsguard.relics;

import com.megacrit.cardcrawl.cards.DamageInfo;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;

public class BlackTortoiseSpirit extends BaseRelic{
    private static final String NAME = "BlackTortoiseSpirit"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public BlackTortoiseSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (player instanceof PlaywrightCharacter && player.currentBlock > 0 && ((PlaywrightCharacter) player).exCharge < 10 && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            ((PlaywrightCharacter) player).increaseEXCharge(1);
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
