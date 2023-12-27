package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import crownsguard.powers.RushCombo;

import static crownsguard.CrownsguardMod.makeID;

public class HeatActionDamage extends AbstractDamageModifier {
    public static final String ID = makeID(HeatActionDamage.class.getSimpleName());
    public HeatActionDamage(){}

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(info.owner, info.owner, new RushCombo(info.owner)));
    }

    @Override
    public boolean ignoresThorns() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new HeatActionDamage();
    }

    public boolean isInherent() {
        return true;
    }
}