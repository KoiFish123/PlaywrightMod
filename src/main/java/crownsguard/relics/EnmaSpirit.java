package crownsguard.relics;


import crownsguard.character.crownsguard.TheCrownsguard;

import static crownsguard.CrownsguardMod.makeID;

public class EnmaSpirit extends BaseRelic{
    private static final String NAME = "EnmaSpirit";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;


    public EnmaSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.flash();
        super.atBattleStartPreDraw();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
