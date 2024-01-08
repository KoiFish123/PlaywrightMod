package crownsguard.powers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModContainer;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.CustomTagEnum;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.damage.CounterDamage;
import crownsguard.damage.EXActionDamage;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class HiddenEXMechanic extends BasePower implements InvisiblePower {
    public static final String POWER_ID = makeID(HiddenEXMechanic.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = CustomTagEnum.EX_MECHANIC;

    private static final boolean TURN_BASED = false;

    private final DamageModContainer container;

    private AbstractCard theCard = null;

    public HiddenEXMechanic(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        updateDescription();
        container = new DamageModContainer(this, new EXActionDamage());
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        int exChargeGain = 1;
        if (owner.hasPower(DrunkPower.POWER_ID)) exChargeGain += (owner.getPower(POWER_ID).amount);

        if (damageAmount > 0 && info.owner instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && !info.owner.hasPower(EXBoost.POWER_ID) && target != info.owner) {
            for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                if (mod instanceof EXActionDamage) {
                    exChargeGain = 0;
                    break;
                }
            }

            ((PlaywrightCharacter) info.owner).increaseEXChargeThroughAttack(exChargeGain);
        }
        super.onAttack(info, damageAmount, target);
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        int exChargeGain = 0;

        if (damageAmount > 0 && info.owner instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && !info.owner.hasPower(EXBoost.POWER_ID) && target != info.owner) {
            for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                if (mod instanceof EXActionDamage) {
                    exChargeGain = 0;
                    break;
                } else if (mod instanceof CounterDamage) {
                    exChargeGain = 1;
                }
            }
            ((PlaywrightCharacter) info.owner).increaseEXChargeThroughAttack(exChargeGain);
        }
        super.onInflictDamage(info, damageAmount, target);
    }

    @Override
    public void onVictory() {
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade() && c.rarity.equals(AbstractCard.CardRarity.BASIC))
                possibleCards.add(c);
        }
        if (!possibleCards.isEmpty()) {
            this.theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            this.theCard.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(this.theCard);
        }
        super.onVictory();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && player instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && player.currentBlock == 0)
            ((PlaywrightCharacter) player).decreaseEXChargeWhenAttacked(1);
        return super.onAttacked(info, damageAmount);
    }
}
