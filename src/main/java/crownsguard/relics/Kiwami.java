package crownsguard.relics;


import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.powers.EXBoostPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.helpers.PowerTip;


import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class Kiwami extends BaseRelic implements ClickableRelic {
    private static final String NAME = "Kiwami";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public boolean activatedThisTurn = false;
    public boolean active = false;

    public int exChargeLossOverTurn = 3;

    public Kiwami() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }

    @Override
    public void atBattleStartPreDraw() {
        deactivateEXBoost();
        description = getUpdatedDescription();
    }

    @Override
    public void atTurnStart() {
        if (((PlaywrightCharacter) player).exCharge == 0) {
            deactivateEXBoost();
            description = getUpdatedDescription();
        }
    }

    @Override
    public void onPlayerEndTurn() {
            if (active) {
                if (player instanceof PlaywrightCharacter) {
                    ((PlaywrightCharacter) player).decreaseEXChargeFromEXBoost(exChargeLossOverTurn);
                }
            }
        super.onPlayerEndTurn();
    }

    @Override
    public void atTurnStartPostDraw() {
        activatedThisTurn = false;
        description = getUpdatedDescription();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (((PlaywrightCharacter) player).exCharge == 0) {
            deactivateEXBoost();
            description = getUpdatedDescription();

        }
    }

    @Override
    public void onRightClick() {
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            if (!activatedThisTurn) {
                this.flash();
                if (!active) {
                    activateEXBoost();
                } else {
                    deactivateEXBoost();
                }
            }
            activatedThisTurn = true;
            description = getUpdatedDescription();
        }
    }

    public void activateEXBoost() {
        addToBot(new ApplyPowerAction(player, player, new EXBoostPower(player, exChargeLossOverTurn)));
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
        this.beginLongPulse();
        active = true;
    }

    public void deactivateEXBoost() {
        addToBot(new RemoveSpecificPowerAction(player, player, player.getPower(EXBoostPower.POWER_ID)));
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
