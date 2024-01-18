package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.PlaywrightMod.makeID;

public class BluntDamage extends AbstractDamageModifier {
    public static final String ID = makeID(BluntDamage.class.getSimpleName());
    public BluntDamage(){}

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BluntDamage();
    }

    public boolean isInherent() {
        return true;
    }
}