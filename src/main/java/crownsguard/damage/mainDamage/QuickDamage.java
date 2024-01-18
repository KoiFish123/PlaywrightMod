package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.PlaywrightMod.makeID;

public class QuickDamage extends AbstractDamageModifier {
    public static final String ID = makeID(QuickDamage.class.getSimpleName());
    public QuickDamage(){}
    @Override
    public AbstractDamageModifier makeCopy() {
        return new QuickDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
