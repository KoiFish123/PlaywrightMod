package crownsguard.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;

public class SwallowSpirit extends BaseRelic{
    private static final String NAME = "SwallowSpirit";
    public static final String ID = makeID(NAME);
    private static final AbstractRelic.RelicTier RARITY = RelicTier.COMMON;
    private static final AbstractRelic.LandingSound SOUND = AbstractRelic.LandingSound.MAGICAL;

    public SwallowSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    public boolean trigger = false;
    public void onPlayerEndTurn() {
        if (player.currentBlock == 0 || this.trigger) {
            this.trigger = false;
            flash();
            stopPulse();
            addToTop(new RelicAboveCreatureAction(player, this));
            ((PlaywrightCharacter) player).increaseEXCharge(3);
        }
    }

    public void atTurnStart() {
        this.trigger = false;
        if (AbstractDungeon.player.currentBlock == 0)
            beginLongPulse();
    }

    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F)
            stopPulse();
        return MathUtils.floor(blockAmount);
    }

    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
