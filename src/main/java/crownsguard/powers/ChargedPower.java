package crownsguard.powers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.damage.HeavyDamage;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;


import java.util.Collections;
import java.util.List;

import static crownsguard.CrownsguardMod.makeID;

public class ChargedPower extends BasePower implements DamageModApplyingPower {
    public static final String POWER_ID = makeID(ChargedPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = false;
    public ChargedPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (owner == player)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL){
            return damage * 1.5F;
        }
        return damage;
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK && list.stream().noneMatch(mod -> mod instanceof HeavyDamage);
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new HeavyDamage());
    }
}
