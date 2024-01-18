package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static crownsguard.PlaywrightMod.makeID;

public class PenetratingDamage extends AbstractDamageModifier {
    public static final String ID = makeID(PenetratingDamage.class.getSimpleName());
    public PenetratingDamage(){}

    @Override
    public boolean ignoresBlock(AbstractCreature target) {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new PenetratingDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
