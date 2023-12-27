package crownsguard.powers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModContainer;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.CustomTagEnum;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.damage.CounterDamage;
import crownsguard.damage.HeatActionDamage;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class HiddenHeatMechanic extends BasePower implements InvisiblePower {
    public static final String POWER_ID = makeID(HiddenHeatMechanic.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = CustomTagEnum.HEAT;

    private static final boolean TURN_BASED = false;

    private final DamageModContainer container;

    public HiddenHeatMechanic(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        updateDescription();
        container = new DamageModContainer(this, new HeatActionDamage());
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        int heatGain = 1;
        if (owner.hasPower(DrunkPower.POWER_ID)) heatGain += (owner.getPower(POWER_ID).amount);

        if (damageAmount > 0 && info.owner instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && !info.owner.hasPower(ExtremeHeatMode.POWER_ID) && target != info.owner) {
            for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                if (mod instanceof HeatActionDamage) {
                    heatGain = 0;
                    break;
                }
            }

            ((PlaywrightCharacter) info.owner).increaseHeatThroughAttack(heatGain);
        }
        super.onAttack(info, damageAmount, target);
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        int heatGain = 0;

        if (damageAmount > 0 && info.owner instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && !info.owner.hasPower(ExtremeHeatMode.POWER_ID) && target != info.owner) {
            for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
                if (mod instanceof HeatActionDamage) {
                    heatGain = 0;
                    break;
                } else if (mod instanceof CounterDamage) {
                    heatGain = 1;
                }
            }
            ((PlaywrightCharacter) info.owner).increaseHeatThroughAttack(heatGain);
        }
        super.onInflictDamage(info, damageAmount, target);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && player instanceof PlaywrightCharacter && info.type == DamageInfo.DamageType.NORMAL && player.currentBlock == 0)
            ((PlaywrightCharacter) player).decreaseHeatWhenAttacked(1);
        return super.onAttacked(info, damageAmount);
    }
}
