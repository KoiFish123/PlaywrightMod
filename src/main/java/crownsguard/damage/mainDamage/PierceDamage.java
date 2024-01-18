package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.PlaywrightMod.makeID;

public class PierceDamage extends AbstractDamageModifier {
    public static final String ID = makeID(PierceDamage.class.getSimpleName());
    public PierceDamage(){}

    @Override
    public AbstractDamageModifier makeCopy() {
        return new PierceDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
