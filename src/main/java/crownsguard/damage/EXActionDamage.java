package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.PlaywrightMod.makeID;

public class EXActionDamage extends AbstractDamageModifier {
    public static final String ID = makeID(EXActionDamage.class.getSimpleName());
    public EXActionDamage(){}

    @Override
    public boolean ignoresThorns() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new EXActionDamage();
    }

    public boolean isInherent() {
        return true;
    }
}