package crownsguard.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import crownsguard.cards.status.Smashed;
import crownsguard.character.PlaywrightCharacter;

import java.util.Random;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class DrunkPower extends BasePower {
    public static final String POWER_ID = makeID(DrunkPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = true;

    public int amount2 = ((PlaywrightCharacter) player).maxDrunk;
    public boolean canGoNegative2 = false;
    protected Color redColor2 = Color.RED.cpy();
    protected Color greenColor2 = Color.GREEN.cpy();

    AbstractCard smashedCard = new Smashed();

    public DrunkPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (amount2 > 0) {
            if (!isTurnBased) {
                greenColor2.a = c.a;
                c = greenColor2;
            }
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont,
                    Integer.toString(amount2), x, y + 15 * Settings.scale, fontScale, c);
        } else if (amount2 < 0 && canGoNegative2) {
            redColor2.a = c.a;
            c = redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont,
                    Integer.toString(amount2), x, y + 15 * Settings.scale, fontScale, c);
        }
    }

    public boolean doesAttackHit() {
        // For every level of Drunk, attacks gain 1 more heat but has a 10% chance of missing

        Random rand = new Random();
        int randomNumber = AbstractDungeon.miscRng.random(1, 10);

        if (randomNumber >= 1 && randomNumber <= this.amount) System.out.println("Attack does not hit");

        return amount <= randomNumber;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL)
            if (!doesAttackHit()){
                AbstractDungeon.effectList.add(new BlockedWordEffect(info.owner, info.owner.hb.cX, info.owner.hb.cY, DESCRIPTIONS[3]));

                damageAmount = 0;
            }
        return super.onAttackToChangeDamage(info, damageAmount);
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        super.atStartOfTurn();
    }

    @Override
    public void onInitialApplication() {
        if (this.amount > ((PlaywrightCharacter) player).maxDrunk) {
            int smashedAmount = amount-((PlaywrightCharacter) player).maxDrunk;
            createSmashed(smashedAmount);
            amount = ((PlaywrightCharacter) player).maxDrunk;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount > ((PlaywrightCharacter) player).maxDrunk) {
            createSmashed(amount - ((PlaywrightCharacter) player).maxDrunk);
            amount = ((PlaywrightCharacter) player).maxDrunk;
        }
        if (this.amount == 0)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DrunkPower.POWER_ID));
    }

    public void createSmashed(int amount) {
            addToTop(new MakeTempCardInDiscardAction(smashedCard, amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (this.amount * 10) + DESCRIPTIONS[2];
    }
}
