package crownsguard.damage.mainDamage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static crownsguard.PlaywrightMod.makeID;

public class SlashDamage extends AbstractDamageModifier {
    public static final String ID = makeID(SlashDamage.class.getSimpleName());
    public SlashDamage(){}

    @Override
    public AbstractDamageModifier makeCopy() {
        return new SlashDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
