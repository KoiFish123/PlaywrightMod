package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import crownsguard.powers.RushComboPower;

import static crownsguard.PlaywrightMod.makeID;

public class LightDamage extends AbstractDamageModifier {
    public static final String ID = makeID(LightDamage.class.getSimpleName());
    public LightDamage(){}

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        addToBot(new ApplyPowerAction(info.owner, info.owner, new RushComboPower(info.owner)));
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new LightDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
