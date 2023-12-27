package crownsguard.cards.attack;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.CounterDamage;
import crownsguard.damage.HeatActionDamage;
import crownsguard.damage.HeavyDamage;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EXUnyieldingCounter extends BaseCard implements ReactionToDamageCard {
    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;

    private static final int WEAK_AMOUNT = 2;
    private static final int UPG_WEAK_AMOUNT = 2;

    public static final String ID = makeID(EXUnyieldingCounter.class.getSimpleName());

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -2
    );

    public EXUnyieldingCounter() {
        super(ID, info, false);
        CardModifierManager.addModifier(this, new ReactionCardMod());
        setSelfRetain(true);
        this.baseMagicNumber = WEAK_AMOUNT;
        this.magicNumber = baseMagicNumber;
        setDamage(DAMAGE, UPG_DAMAGE);
        DamageModifierManager.addModifier(this, new HeatActionDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
        DamageModifierManager.addModifier(this, new CounterDamage());
        // Since this is a Heat Action, I expect HiddenHeatMechanic to give no heat, despite being dealing Counter damage
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPG_WEAK_AMOUNT);
            upgradeName();
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {
        if (amount > player.currentBlock && player instanceof PlaywrightCharacter && ((PlaywrightCharacter) player).heat >= 5) {
            addToTop(new DiscardSpecificCardAction(this));
            addToTop(new DamageAction(info.owner, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToTop(
                    new AbstractGameAction() {
                        @Override
                        public void update() {
                            this.isDone = true;
                            ((PlaywrightCharacter)player).decreaseHeatWhenAttack(5);
                        }
                    }
            );
            addToTop(new ApplyPowerAction(info.owner, player,new WeakPower(info.owner,magicNumber,false)));

            return amount;
        }
        return amount;
    }
}
