package crownsguard.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import crownsguard.cards.status.Smashed;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.damage.DrunkenAccurateDamage;

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

    protected int tempMaxDrunk = 5;

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
        // For every level of Drunk, attacks gain 1 more EX Charge but has a 10% chance of missing

        Random rand = new Random();
        int randomNumber = AbstractDungeon.miscRng.random(1, 10);

        if (randomNumber >= 1 && randomNumber <= this.amount) System.out.println("Attack does not hit");

        return amount <= randomNumber;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {

        // // For Playwright creature (with EX mechanic)
        if (info.owner instanceof PlaywrightCharacter) {
            if (info.type == DamageInfo.DamageType.NORMAL) {
                for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                    if (mod instanceof DrunkenAccurateDamage) {
                        return super.onAttackToChangeDamage(info, damageAmount);
                    }
                }
                if (!doesAttackHit()) {
                    damageAmount = 0;
                }
            }
        }

        // For non-Playwright creature (no EX mechanic)
        else {
            if (info.type == DamageInfo.DamageType.NORMAL) {

                for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                    if (mod instanceof DrunkenAccurateDamage) {
                        return super.onAttackToChangeDamage(info, damageAmount);
                    }
                }

                damageAmount = doesAttackHit() ? 0 : amount * 2;
            }
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
        smashedBehavior();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        smashedBehavior();
        if (this.amount == 0)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DrunkPower.POWER_ID));
    }

    private void smashedBehavior() {

        // For player
        if (owner.equals(player)) {

            // For Playwright character
            if (this.amount > ((PlaywrightCharacter) player).maxDrunk) {
                int smashedAmount = amount - ((PlaywrightCharacter) player).maxDrunk;
                createSmashed(smashedAmount);
                amount = ((PlaywrightCharacter) player).maxDrunk;
            }

            // For non-Playwright character
            else if (!(owner instanceof PlaywrightCharacter)) {
                if (this.amount > tempMaxDrunk) {
                    int smashedAmount = amount - (tempMaxDrunk);
                    createSmashed(smashedAmount);
                    amount = tempMaxDrunk;
                }
            }
        }

        // For monster: Lose X strength for 1 turn
        else if (owner instanceof AbstractMonster) {
            if (this.amount > tempMaxDrunk) {
                int smashedAmount = amount - (tempMaxDrunk);
                addToBot(new ApplyPowerAction(owner, owner, new GainStrengthPower(this.owner, smashedAmount), smashedAmount));
                addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(this.owner, -smashedAmount), -smashedAmount));
                amount = tempMaxDrunk;
            }
        }
    }

    public void createSmashed(int amount) {
        addToTop(new MakeTempCardInDiscardAction(smashedCard, amount));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (this.amount * 10) + DESCRIPTIONS[2];
    }
}
