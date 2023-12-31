package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import crownsguard.powers.BludgeoningResistance;

import static crownsguard.CrownsguardMod.makeID;

public class BludgeoningDamage extends AbstractDamageModifier {
    public static final String ID = makeID(BludgeoningDamage.class.getSimpleName());
    public BludgeoningDamage(){}

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BludgeoningDamage();
    }

    public boolean isInherent() {
        return true;
    }
}