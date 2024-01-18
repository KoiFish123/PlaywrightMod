package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import crownsguard.damage.mainDamage.BluntDamage;

import static crownsguard.PlaywrightMod.makeID;

public class CounterDamage extends AbstractDamageModifier {
    public static final String ID = makeID(BluntDamage.class.getSimpleName());
    public CounterDamage(){}

    /*
    This is a dummy damage modifier
    So that OnInflictDamage in HiddenHeatMechanic can find it and increase heat
    Usually used for reaction that counter/attack
     */

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BluntDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
