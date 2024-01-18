package crownsguard.cards.attack;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.damage.CounterDamage;
import crownsguard.damage.mainDamage.LightDamage;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class ResoluteCounter extends BaseCard implements ReactionToDamageCard {
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    public static final String ID = makeID(ResoluteCounter.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.NONE,
            -2
    );

    public ResoluteCounter() {
        super(ID, info, false);
        CardModifierManager.addModifier(this, new ReactionCardMod());
        DamageModifierManager.addModifier(this, new CounterDamage());
        setSelfRetain(true);
        setDamage(DAMAGE, UPG_DAMAGE);
        DamageModifierManager.addModifier(this, new LightDamage());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {
        if (amount > player.currentBlock) {
            addToTop(new DiscardSpecificCardAction(this));
            calculateCardDamage((AbstractMonster)info.owner);
            addToTop(new DamageAction(info.owner, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            return amount;
        }
        return amount;
    }
}
