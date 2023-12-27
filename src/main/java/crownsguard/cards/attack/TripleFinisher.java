package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.HeavyDamage;
import crownsguard.util.CardStats;


public class TripleFinisher extends BaseCard {
    //makeID adds the mod ID, so the final ID will be something like "modID:EvasiveStrike"
    public static final String ID = makeID(TripleFinisher.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.SPECIAL, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 8;

    public TripleFinisher() {
        super(ID, info,true); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE); //Sets the card's damage and how much it changes when upgraded.
//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
        this.exhaust = true;
        this.isEthereal = true;
        this.magicNumber = 3;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (p instanceof PlaywrightCharacter)
        AbstractDungeon.actionManager.addToBottom(
                new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        ((PlaywrightCharacter)p).increaseHeat(magicNumber);
                    }
                }
        );
    }

}