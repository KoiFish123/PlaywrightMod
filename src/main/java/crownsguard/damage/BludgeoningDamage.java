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

//    @Override
//    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
//        if (target != null)
//            if (target.hasPower(BludgeoningResistance.POWER_ID))
//                return super.atDamageFinalGive(damage/2, type, target, card);
//        return super.atDamageFinalGive(damage, type, target, card);
//    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new BludgeoningDamage();
    }

    public boolean isInherent() {
        return true;
    }
}