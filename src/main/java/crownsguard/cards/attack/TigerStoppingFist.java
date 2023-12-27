package crownsguard.cards.attack;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.CounterDamage;
import crownsguard.damage.HeavyDamage;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class TigerStoppingFist extends BaseCard implements ReactionToDamageCard {

    public static final String ID = makeID(TigerStoppingFist.class.getSimpleName());

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 10;

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    public TigerStoppingFist(){
        super(ID,info);
        CardModifierManager.addModifier(this,new ReactionCardMod());
        setSelfRetain(true);
//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
        DamageModifierManager.addModifier(this, new CounterDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
        setDamage(DAMAGE,UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;

        if (player instanceof PlaywrightCharacter) {
            this.baseDamage += ((PlaywrightCharacter) player).maxHeat - ((PlaywrightCharacter) player).heat;
        }

        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified= (this.damage != this.baseDamage);
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {

        if (amount > player.currentBlock) {
            addToTop(new DiscardSpecificCardAction(this));
            addToTop(new DamageAction(info.owner, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

            return 0;
        }
        return amount;
    }
}
