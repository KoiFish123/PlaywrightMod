package crownsguard.relics;


import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.powers.ExtremeHeatMode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.helpers.PowerTip;


import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class Kiwami extends BaseRelic implements ClickableRelic {
    private static final String NAME = "Kiwami"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public boolean activatedThisTurn = false;
    public boolean active = false;

    public int heatLossOverTurn = 3;

    public Kiwami() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void atBattleStartPreDraw() {
        deactivateExtremeHeat();
        description = getUpdatedDescription();
    }

    @Override
    public void atTurnStart() {
        if (((PlaywrightCharacter) player).heat == 0) {
            deactivateExtremeHeat();
            description = getUpdatedDescription();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (active) {
            if (player instanceof PlaywrightCharacter) {
                ((PlaywrightCharacter) player).decreaseHeatFromExtremeHeat(heatLossOverTurn);
            }
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        activatedThisTurn = false;
        description = getUpdatedDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (((PlaywrightCharacter) player).heat == 0) {
            deactivateExtremeHeat();
            description = getUpdatedDescription();

        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            if (!activatedThisTurn) {
                if (!active) {
                    activateExtremeHeat();
                } else {
                    deactivateExtremeHeat();
                }
            }
            activatedThisTurn = true;
            description = getUpdatedDescription();
        }
    }

    public void activateExtremeHeat() {
        addToBot(new ApplyPowerAction(player, player, new ExtremeHeatMode(player, heatLossOverTurn)));
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
        this.beginLongPulse();
        active = true;
    }

    public void deactivateExtremeHeat() {
        addToBot(new RemoveSpecificPowerAction(player, player, player.getPower(ExtremeHeatMode.POWER_ID)));
        this.stopPulse();
        active = false;
    }

    @Override
    public void atPreBattle() {
        active = false;
        activatedThisTurn = false;
        description = getUpdatedDescription();
    }
    private String getDescription() {
        if (!active) {if (activatedThisTurn){
            return DESCRIPTIONS[0] + DESCRIPTIONS[2];
        }
            if (!activatedThisTurn){
                return DESCRIPTIONS[0] + DESCRIPTIONS[3];
            }
        }
        if (active) {
            if (activatedThisTurn){
                return DESCRIPTIONS[1] + DESCRIPTIONS[2];
            }
            if (!activatedThisTurn){
                return DESCRIPTIONS[1] + DESCRIPTIONS[3];
            }
        }
        return null;
    }
    @Override
    public String getUpdatedDescription() {

        description = getDescription();

        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();

        return description;
    }
}
