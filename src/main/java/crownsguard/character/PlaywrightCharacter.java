package crownsguard.character;

import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import crownsguard.powers.EXBoost;
import crownsguard.powers.HiddenEXMechanic;
import crownsguard.relics.AzureDragonSpirit;
import crownsguard.relics.EnmaSpirit;
import crownsguard.relics.WhiteTigerSpirit;
import crownsguard.relics.YellowDragonSpirit;


import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/*
- This is where I go in detail for my custom characters
- Most (if not all) of them will use a resource call EX Gauge
- I know there's a lot of details, but I want the gameplay to be as fluid as possible
 */
public class PlaywrightCharacter extends CustomPlayer implements EXInterface {

    public int maxDrunk;

    public int exCharge;

    public int maxEXCharge;

    private float exBarWidth;

    private float targetEXBarWidth;

    private static final float EX_BAR_HEIGHT = 20.0F * Settings.scale;

    private static final float EX_BG_OFFSET_X = 31.0F * Settings.scale;

    private static final float EX_BAR_OFFSET_Y = -28.0F * Settings.scale;

    private static final float EX_TEXT_OFFSET_Y = 6.0F * Settings.scale;


    private Color hbBgColor;

    private Color hbShadowColor;

    private Color lightHbBarColor;

    private Color darkHbBarColor;

    private Color hbTextColor;

    private float EXHideTimer = 0.0F;

    private float exBarAnimTimer = 0.0F;

    public boolean hideEX;

    private float particleTimer = 0.0F;

    private String currentPowerIncrease = null;

    public PlaywrightCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, AbstractAnimation animation, Color hbBgColor, Color hbShadowColor, Color lightHbBarColor, Color darkHbBarColor, Color hbTextColor) {
        super(name, playerClass, energyOrbInterface, animation);
        this.hbBgColor = hbBgColor;
        this.hbShadowColor = hbShadowColor;
        this.lightHbBarColor = lightHbBarColor;
        this.darkHbBarColor = darkHbBarColor;
        this.hbTextColor = hbTextColor;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return null;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return null;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return null;
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return null;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return null;
    }

    @Override
    public Color getCardRenderColor() {
        return null;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return null;
    }

    @Override
    public Color getCardTrailColor() {
        return null;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return null;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return null;
    }

    @Override
    public AbstractPlayer newInstance() {
        return null;
    }

    @Override
    public String getSpireHeartText() {
        return null;
    }

    @Override
    public Color getSlashAttackColor() {
        return null;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return null;
    }

    @Override
    public void applyStartOfCombatLogic() {
        if (!this.hasRelic(EnmaSpirit.ID))
            this.exCharge = 0;

        this.maxEXCharge = 15;

        if (this.hasRelic(AzureDragonSpirit.ID))
            this.maxEXCharge = 20;

        addPower(new HiddenEXMechanic(player));
        exGaugeBarUpdatedEvent();
        super.applyStartOfCombatLogic();
    }
    
    public boolean exGaugeIncreasedThisTurn = false;

    public void increaseEXCharge(int num) {
        // Increase EX Gauge gain from source other thank attack while holding this relic
        if (player.hasRelic(WhiteTigerSpirit.ID))
            num += 1;

        if (player.hasPower(EXBoost.POWER_ID) && num > 0)
            num = 0;

        exGaugeIncreasedThisTurn = true;
        updateEXGauge(num);
    }

    public void increaseEXChargeThroughAttack(int num) {
        if (player.hasPower(EXBoost.POWER_ID) && num > 0) {
            num = 0;
        }
        exGaugeIncreasedThisTurn = true;
        updateEXGauge(num);
    }

    public void decreaseEXCharge(int num) {
        updateEXGauge(-num);
    }

    public void decreaseEXChargeWhenAttack(int num) {
        updateEXGauge(-num);

        // If has relic YellowDragonSpirit, heal 1
        if (player.hasRelic(YellowDragonSpirit.ID))
            AbstractDungeon.actionManager.addToTop(new HealAction(player,player,1));
    }

    public void decreaseEXChargeWhenAttacked(int num) {
        if (player.hasPower(EXBoost.POWER_ID) && num > 0) {
            num = 0;
        }
        updateEXGauge(-num);
    }

    public void decreaseEXChargeFromEXBoost(int exGaugeOnActivation) {
        this.exCharge = Math.min(this.maxEXCharge, Math.max(0, this.exCharge - exGaugeOnActivation));
        exGaugeBarUpdatedEvent();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        if (!exGaugeIncreasedThisTurn && !player.hasPower(EXBoost.POWER_ID)) decreaseEXCharge(1);
        exGaugeIncreasedThisTurn = false;
        super.applyEndOfTurnTriggers();
    }

    public void updateEXGauge(int num) {
        int oldEXGauge = this.exCharge;
        this.exCharge = Math.min(this.maxEXCharge, Math.max(0, this.exCharge + num));

        int oldPower = oldEXGauge / 5;
        int newPower = this.exCharge / 5;

        if (newPower != oldPower) {
            adjustPower(newPower - oldPower, currentPowerIncrease);
        }
        exGaugeBarUpdatedEvent();
    }

    public void updateEXGaugeAfterExitEXBoost(int exGaugeOnActivation) {

        int oldPower = exGaugeOnActivation / 5;
        int newPower = this.exCharge / 5;

        if (newPower != oldPower) {
            adjustPower(newPower - oldPower, currentPowerIncrease);
        }
        exGaugeBarUpdatedEvent();
    }

    private void adjustPower(int powerDifference, String powerToAdjust) {
        if (powerToAdjust.equals(null)){
            return;
        }
        if (powerToAdjust.equals(DexterityPower.POWER_ID)) {
            adjustDexterityPower(powerDifference);
        } else if (powerToAdjust.equals(StrengthPower.POWER_ID)) {
            adjustStrengthPower(powerDifference);
        }
    }

    private void adjustDexterityPower(int powerDifference){
        if (powerDifference > 0){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new DexterityPower(player, powerDifference)));
        }
        if (powerDifference < 0) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(player, player, DexterityPower.POWER_ID, -powerDifference));
        }
    }

    private void adjustStrengthPower(int powerDifference){
        if (powerDifference > 0){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(player, player, new StrengthPower(player, powerDifference)));
        }
        if (powerDifference < 0) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(player, player, StrengthPower.POWER_ID, -powerDifference));
        }
    }

    public void transferPowerOnStanceChange(String newPowerIncrease){
        int powerAdjust = exCharge /5;
        System.out.println(currentPowerIncrease);
        System.out.println(newPowerIncrease);

        if (currentPowerIncrease != null)
            adjustPower(-powerAdjust, currentPowerIncrease);
        if (newPowerIncrease != null) {
            currentPowerIncrease = newPowerIncrease;

            System.out.println(currentPowerIncrease);
            System.out.println(newPowerIncrease);

            adjustPower(powerAdjust, newPowerIncrease);
        }
    }

    public void exGaugeBarUpdatedEvent() {
        this.exBarAnimTimer = 1.2F;
        this.targetEXBarWidth = this.hb.width * this.exCharge / this.maxEXCharge;
        if (this.maxEXCharge == this.exCharge) {
            this.exBarWidth = this.targetEXBarWidth;
        } else if (this.exCharge == 0) {
            this.exBarWidth = 0.0F;
            this.targetEXBarWidth = 0.0F;
        }
        if (this.targetEXBarWidth > this.exBarWidth)
            this.exBarWidth = this.targetEXBarWidth;
    }

    protected void updateHealthBar() {
        super.updateHealthBar();
        updateEbHoverFade();
        updateEbAlpha();
        updateEbDamageAnimation();
    }

    private void updateEbHoverFade() {
        if (this.healthHb.hovered) {
            this.EXHideTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.EXHideTimer < 0.2F)
                this.EXHideTimer = 0.2F;
        } else {
            this.EXHideTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.EXHideTimer > 1.0F)
                this.EXHideTimer = 1.0F;
        }
    }

    private void updateEbAlpha() {
        if (this.hb.hovered && this.exCharge >= 20) {
            this.hbShadowColor = new Color(0.67058825F, 0.9764706F, 1.0F, 0.0F);
        } else {
            this.hbShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        }
        if (this.isEscaping) {
            this.targetEXBarWidth = 0.0F;
            this.hbBgColor.a = this.hbAlpha * 0.75F;
            this.hbShadowColor.a = this.hbAlpha * 0.5F;
        } else {
            this.hbBgColor.a = this.hbAlpha * 0.5F;
            this.hbShadowColor.a = this.hbAlpha * 0.2F;
        }
        if (this.hb.hovered && this.exCharge >= 20)
            this.hbShadowColor.a = this.hbAlpha * 0.8F;
        this.hbTextColor.a = this.hbAlpha;
        this.lightHbBarColor.a = this.hbAlpha;
        this.darkHbBarColor.a = this.hbAlpha;
    }

    private void updateEbDamageAnimation() {
        if (this.exBarAnimTimer > 0.0F)
            this.exBarAnimTimer -= Gdx.graphics.getDeltaTime();
        if (this.exBarWidth != this.targetEXBarWidth && this.exBarAnimTimer <= 0.0F && this.targetEXBarWidth < this.exBarWidth)
            this.exBarWidth = MathHelper.uiLerpSnap(this.exBarWidth, this.targetEXBarWidth);
    }

    public void renderHealth(SpriteBatch sb) {
        super.renderHealth(sb);
        if (Settings.hideCombatElements)
            return;
        if (this.hideEX)
            return;
        float x = this.hb.cX - this.hb.width / 2.0F;
        float y = this.hb.cY + this.hb.height / 2.0F;
        renderEXBarBg(sb, x, y);
        renderWhiteStanceBar(sb, x, y);
        renderGoldenStanceBar(sb, x, y);
        renderStanceText(sb, y);
    }

    private void renderEXBarBg(SpriteBatch sb, float x, float y) {
        sb.setColor(this.hbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - EX_BAR_HEIGHT, y - EX_BG_OFFSET_X + 3.0F * Settings.scale, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - EX_BG_OFFSET_X + 3.0F * Settings.scale, this.hb.width, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.hb.width, y - EX_BG_OFFSET_X + 3.0F * Settings.scale, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
        sb.setColor(this.hbBgColor);
        if (this.exCharge != this.maxEXCharge) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - EX_BAR_HEIGHT, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + EX_BAR_OFFSET_Y, this.hb.width, EX_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
        }
    }

    private void renderWhiteStanceBar(SpriteBatch sb, float x, float y) {
        sb.setColor(this.lightHbBarColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - EX_BAR_HEIGHT, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + EX_BAR_OFFSET_Y, this.exBarWidth, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.exBarWidth, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
    }

    private void renderGoldenStanceBar(SpriteBatch sb, float x, float y) {
        sb.setColor(this.darkHbBarColor);
        if (this.exCharge > 0)
            sb.draw(ImageMaster.HEALTH_BAR_L, x - EX_BAR_HEIGHT, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + EX_BAR_OFFSET_Y, this.targetEXBarWidth, EX_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetEXBarWidth, y + EX_BAR_OFFSET_Y, EX_BAR_HEIGHT, EX_BAR_HEIGHT);
    }

    private void renderStanceText(SpriteBatch sb, float y) {
        float tmp = this.hbTextColor.a;
        this.hbTextColor.a *= this.EXHideTimer;
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.exCharge + "/" + this.maxEXCharge, this.hb.cX, y + EX_BAR_OFFSET_Y + EX_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.hbTextColor);
        this.hbTextColor.a = tmp;
    }

    public void exGaugeColorChangeOnStanceChange(Color lightHbBarColor, Color darkHbBarColor, Color hbTextColor) {
        this.lightHbBarColor = lightHbBarColor;
        this.darkHbBarColor = darkHbBarColor;
        this.hbTextColor = hbTextColor;
        exGaugeBarUpdatedEvent();
    }
}
