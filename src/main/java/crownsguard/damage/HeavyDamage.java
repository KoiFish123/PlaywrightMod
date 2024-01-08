package crownsguard.damage;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import crownsguard.character.PlaywrightCharacter;

import static crownsguard.CrownsguardMod.makeID;

public class HeavyDamage extends AbstractDamageModifier{
    public static final String ID = makeID(HeavyDamage.class.getSimpleName());
    public HeavyDamage(){}

    @Override
    public void onDamageModifiedByBlock(DamageInfo info, int unblockedAmount, int blockedAmount, AbstractCreature target) {
        if (unblockedAmount >= 0 && blockedAmount > 0){
            addToTop(new ApplyPowerAction(target, info.owner, new VulnerablePower(target, 1, false)));
            addToTop(new ApplyPowerAction(target, info.owner, new WeakPower(target, 1, false)));
        }
        super.onDamageModifiedByBlock(info, unblockedAmount, blockedAmount, target);
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new HeavyDamage();
    }

    public boolean isInherent() {
        return true;
    }
}
