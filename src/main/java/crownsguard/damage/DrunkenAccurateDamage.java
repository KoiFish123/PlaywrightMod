package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.CrownsguardMod.makeID;

/*
This for attack that automatically hit even if you are Drunk
 */
public class DrunkenAccurateDamage extends AbstractDamageModifier {
    public static final String ID = makeID(DrunkenAccurateDamage.class.getSimpleName());
    public DrunkenAccurateDamage(){}

    @Override
    public AbstractDamageModifier makeCopy() {
        return new DrunkenAccurateDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
