package crownsguard.powers;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.stances.TankStance;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class CollateralDamagePower extends BasePower {
    public static final String POWER_ID = makeID(CollateralDamagePower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;

    public CollateralDamagePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (owner instanceof PlaywrightCharacter) {
            if (player.stance.ID.equals(TankStance.STANCE_ID))
                if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS) {
                    addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
                }
        }
        super.onAttack(info, damageAmount, target);
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
