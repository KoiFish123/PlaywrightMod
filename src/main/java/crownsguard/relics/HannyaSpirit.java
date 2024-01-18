package crownsguard.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.StrengthPower;
import crownsguard.character.crownsguard.TheCrownsguard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;

public class HannyaSpirit extends BaseRelic{
    private static final String NAME = "HannyaSpirit";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public HannyaSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    private static final int STRENGTH_GAIN = 3;

    private static boolean usedThisCombat = false;

    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        this.pulse = true;
        beginPulse();
    }

    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, STRENGTH_GAIN), STRENGTH_GAIN));
        addToTop(new RelicAboveCreatureAction(player, this));
    }

    public void wasHPLost(int damageAmount) {
        if (damageAmount > 0 &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !usedThisCombat) {
            flash();
            this.pulse = false;
            addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, -STRENGTH_GAIN), -STRENGTH_GAIN));
            addToTop(new RelicAboveCreatureAction(player, this));
            usedThisCombat = true;
            this.grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
