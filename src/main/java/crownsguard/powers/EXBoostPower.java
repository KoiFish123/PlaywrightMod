package crownsguard.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.stances.BruiserStance;
import crownsguard.stances.TankStance;
import crownsguard.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;
import static crownsguard.util.TextureLoader.getTexture;

public class EXBoostPower extends BasePower {
    public static final String POWER_ID = makeID(EXBoostPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    private int exChargeOnActivation;

    private int bonus;

    private int exChargeLossOverTurn;


    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) damage += bonus;

        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        blockAmount += bonus;
        return blockAmount;
    }

    @Override
    public void atStartOfTurn() {
        if (player instanceof PlaywrightCharacter) {
            if (((PlaywrightCharacter) player).exCharge > 0) {
                addToBot(new GainEnergyAction(1));
                addToBot(new DrawCardAction(1));
            } else
                addToBot(new RemoveSpecificPowerAction(player, player, this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (player instanceof PlaywrightCharacter) {
                ((PlaywrightCharacter) player).decreaseEXCharge(exChargeLossOverTurn);
            }
        }
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (((PlaywrightCharacter) player).exCharge == 0) {
            addToBot(new RemoveSpecificPowerAction(player, player, this));
        }
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        changeTextureDependsOnStance(newStance);

        super.onChangeStance(oldStance, newStance);
    }

    @Override
    public void onInitialApplication() {
        if (owner instanceof PlaywrightCharacter)
            changeTextureDependsOnStance(player.stance);
        super.onInitialApplication();
    }

    public void changeTextureDependsOnStance(AbstractStance newStance) {
        if (newStance != null)
            this.img = null;

        String textureID = null;

        if (newStance.ID.equals(TankStance.STANCE_ID))
            textureID = "EXBoostTank";
        else if (newStance.ID.equals(BruiserStance.STANCE_ID))
            textureID = "EXBoostBruiser";

        Texture normalTexture = TextureLoader.getPowerTexture(textureID);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(textureID);


        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }

    @Override
    public void onRemove() {
        ((PlaywrightCharacter) player).updateEXGaugeAfterExitEXBoost(exChargeOnActivation);
        super.onRemove();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.bonus + DESCRIPTIONS[1] + this.bonus + DESCRIPTIONS[2] + exChargeLossOverTurn + DESCRIPTIONS[3];
    }

    public EXBoostPower(AbstractCreature owner, int exChargeLossOverTurn) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        if (owner instanceof PlaywrightCharacter) exChargeOnActivation = ((PlaywrightCharacter) owner).exCharge;

        this.bonus = exChargeOnActivation / 3;

        this.exChargeLossOverTurn = exChargeLossOverTurn;

        updateDescription();
    }
}
