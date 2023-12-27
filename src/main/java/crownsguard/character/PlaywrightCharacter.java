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
import crownsguard.powers.ExtremeHeatMode;
import crownsguard.powers.HiddenHeatMechanic;
import crownsguard.relics.AzureDragonSpirit;
import crownsguard.relics.EnmaSpirit;
import crownsguard.relics.WhiteTigerSpirit;
import crownsguard.relics.YellowDragonSpirit;


import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/*
- This is where I go in detail for my custom characters
- Most (if not all) of them will use a resource call Heat
- I know there's a lot of details, but I want the gameplay to be as fluid as possible
 */
public class PlaywrightCharacter extends CustomPlayer implements HeatInterface {

    public int maxDrunk;

    public int heat;

    public int maxHeat;

    private float heatBarWidth;

    private float targetHeatBarWidth;

    private static final float HEAT_BAR_HEIGHT = 20.0F * Settings.scale;

    private static final float HEAT_BG_OFFSET_X = 31.0F * Settings.scale;

    private static final float HEAT_BAR_OFFSET_Y = -28.0F * Settings.scale;

    private static final float HEAT_TEXT_OFFSET_Y = 6.0F * Settings.scale;


    private Color hbBgColor;

    private Color hbShadowColor;

    private Color lightHbBarColor;

    private Color darkHbBarColor;

    private Color hbTextColor;

    private float heatHideTimer = 0.0F;

    private float heatBarAnimTimer = 0.0F;

    public boolean hideHeat;

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
            this.heat = 0;

        this.maxHeat = 15;

        if (this.hasRelic(AzureDragonSpirit.ID))
            this.maxHeat = 20;

        addPower(new HiddenHeatMechanic(player));
        heatBarUpdatedEvent();
        super.applyStartOfCombatLogic();
    }
    
    public boolean heatIncreasedThisTurn = false;

    public void increaseHeat(int num) {
        // Increase heat gain from source other thank attack while holding this relic
        if (player.hasRelic(WhiteTigerSpirit.ID))
            num += 1;

        if (player.hasPower(ExtremeHeatMode.POWER_ID) && num > 0)
            num = 0;

        heatIncreasedThisTurn = true;
        updateHeat(num);
    }

    public void increaseHeatThroughAttack(int num) {
        if (player.hasPower(ExtremeHeatMode.POWER_ID) && num > 0) {
            num = 0;
        }
        heatIncreasedThisTurn = true;
        updateHeat(num);
    }

    public void decreaseHeat(int num) {
        updateHeat(-num);
    }

    public void decreaseHeatWhenAttack(int num) {
        updateHeat(-num);

        // If has relic YellowDragonSpirit, heal 1
        if (player.hasRelic(YellowDragonSpirit.ID))
            AbstractDungeon.actionManager.addToTop(new HealAction(player,player,1));
    }

    public void decreaseHeatWhenAttacked(int num) {
        if (player.hasPower(ExtremeHeatMode.POWER_ID) && num > 0) {
            num = 0;
        }
        updateHeat(-num);
    }

    public void decreaseHeatFromExtremeHeat(int heatOnActivation) {
        this.heat = Math.min(this.maxHeat, Math.max(0, this.heat - heatOnActivation));
        heatBarUpdatedEvent();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        if (!heatIncreasedThisTurn && !player.hasPower(ExtremeHeatMode.POWER_ID)) decreaseHeat(1);
        heatIncreasedThisTurn = false;
    }

    public void updateHeat(int num) {
        int oldHeat = this.heat;
        this.heat = Math.min(this.maxHeat, Math.max(0, this.heat + num));

        int oldPower = oldHeat / 5;
        int newPower = this.heat / 5;

        if (newPower != oldPower) {
            adjustPower(newPower - oldPower, currentPowerIncrease);
        }
        heatBarUpdatedEvent();
    }

    public void updateHeatAfterExitExtremeHeat(int heatOnActivation) {

        int oldPower = heatOnActivation / 5;
        int newPower = this.heat / 5;

        if (newPower != oldPower) {
            adjustPower(newPower - oldPower, currentPowerIncrease);
        }
        heatBarUpdatedEvent();
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
        int powerAdjust = heat/5;
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

    public void heatBarUpdatedEvent() {
        this.heatBarAnimTimer = 1.2F;
        this.targetHeatBarWidth = this.hb.width * this.heat / this.maxHeat;
        if (this.maxHeat == this.heat) {
            this.heatBarWidth = this.targetHeatBarWidth;
        } else if (this.heat == 0) {
            this.heatBarWidth = 0.0F;
            this.targetHeatBarWidth = 0.0F;
        }
        if (this.targetHeatBarWidth > this.heatBarWidth)
            this.heatBarWidth = this.targetHeatBarWidth;
    }

    protected void updateHealthBar() {
        super.updateHealthBar();
        updateEbHoverFade();
        updateEbAlpha();
        updateEbDamageAnimation();
    }

    private void updateEbHoverFade() {
        if (this.healthHb.hovered) {
            this.heatHideTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.heatHideTimer < 0.2F)
                this.heatHideTimer = 0.2F;
        } else {
            this.heatHideTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.heatHideTimer > 1.0F)
                this.heatHideTimer = 1.0F;
        }
    }

    private void updateEbAlpha() {
        if (this.hb.hovered && this.heat >= 20) {
            this.hbShadowColor = new Color(0.67058825F, 0.9764706F, 1.0F, 0.0F);
        } else {
            this.hbShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        }
        if (this.isEscaping) {
            this.targetHeatBarWidth = 0.0F;
            this.hbBgColor.a = this.hbAlpha * 0.75F;
            this.hbShadowColor.a = this.hbAlpha * 0.5F;
        } else {
            this.hbBgColor.a = this.hbAlpha * 0.5F;
            this.hbShadowColor.a = this.hbAlpha * 0.2F;
        }
        if (this.hb.hovered && this.heat >= 20)
            this.hbShadowColor.a = this.hbAlpha * 0.8F;
        this.hbTextColor.a = this.hbAlpha;
        this.lightHbBarColor.a = this.hbAlpha;
        this.darkHbBarColor.a = this.hbAlpha;
    }

    private void updateEbDamageAnimation() {
        if (this.heatBarAnimTimer > 0.0F)
            this.heatBarAnimTimer -= Gdx.graphics.getDeltaTime();
        if (this.heatBarWidth != this.targetHeatBarWidth && this.heatBarAnimTimer <= 0.0F && this.targetHeatBarWidth < this.heatBarWidth)
            this.heatBarWidth = MathHelper.uiLerpSnap(this.heatBarWidth, this.targetHeatBarWidth);
    }

    public void renderHealth(SpriteBatch sb) {
        super.renderHealth(sb);
        if (Settings.hideCombatElements)
            return;
        if (this.hideHeat)
            return;
        float x = this.hb.cX - this.hb.width / 2.0F;
        float y = this.hb.cY + this.hb.height / 2.0F;
        renderHeatBg(sb, x, y);
        renderWhiteStanceBar(sb, x, y);
        renderGoldenStanceBar(sb, x, y);
        renderStanceText(sb, y);
    }

    private void renderHeatBg(SpriteBatch sb, float x, float y) {
        sb.setColor(this.hbShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - HEAT_BAR_HEIGHT, y - HEAT_BG_OFFSET_X + 3.0F * Settings.scale, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - HEAT_BG_OFFSET_X + 3.0F * Settings.scale, this.hb.width, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.hb.width, y - HEAT_BG_OFFSET_X + 3.0F * Settings.scale, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
        sb.setColor(this.hbBgColor);
        if (this.heat != this.maxHeat) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEAT_BAR_HEIGHT, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEAT_BAR_OFFSET_Y, this.hb.width, HEAT_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
        }
    }

    private void renderWhiteStanceBar(SpriteBatch sb, float x, float y) {
        sb.setColor(this.lightHbBarColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - HEAT_BAR_HEIGHT, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEAT_BAR_OFFSET_Y, this.heatBarWidth, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.heatBarWidth, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
    }

    private void renderGoldenStanceBar(SpriteBatch sb, float x, float y) {
        sb.setColor(this.darkHbBarColor);
        if (this.heat > 0)
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEAT_BAR_HEIGHT, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEAT_BAR_OFFSET_Y, this.targetHeatBarWidth, HEAT_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.targetHeatBarWidth, y + HEAT_BAR_OFFSET_Y, HEAT_BAR_HEIGHT, HEAT_BAR_HEIGHT);
    }

    private void renderStanceText(SpriteBatch sb, float y) {
        float tmp = this.hbTextColor.a;
        this.hbTextColor.a *= this.heatHideTimer;
//        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.heat + "/" + this.maxHeat + "(" + this.totalHeat + ")", this.hb.cX, y + HEAT_BAR_OFFSET_Y + HEAT_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.hbTextColor);
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.heat + "/" + this.maxHeat, this.hb.cX, y + HEAT_BAR_OFFSET_Y + HEAT_TEXT_OFFSET_Y + 5.0F * Settings.scale, this.hbTextColor);
        this.hbTextColor.a = tmp;
    }

    public void heatColorChangeOnStanceChange(Color lightHbBarColor, Color darkHbBarColor, Color hbTextColor) {
        this.lightHbBarColor = lightHbBarColor;
        this.darkHbBarColor = darkHbBarColor;
        this.hbTextColor = hbTextColor;
        heatBarUpdatedEvent();
    }
}
