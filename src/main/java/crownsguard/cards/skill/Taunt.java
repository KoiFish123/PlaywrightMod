package crownsguard.cards.skill;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.util.CardStats;

public class Taunt extends BaseCard {
    public static final String ID = makeID(Taunt.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    // Todo: Reduce HEAT_GAIN back after test
    public int HEAT_GAIN = 10;
    public int UPG_HEAT_GAIN = 1;

    public Taunt() {
        super(ID, info,true); //Pass the required information to the BaseCard constructor.
        this.baseMagicNumber = HEAT_GAIN;
        this.magicNumber = baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_HEAT_GAIN);
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (abstractPlayer instanceof PlaywrightCharacter) ((PlaywrightCharacter) abstractPlayer).increaseHeat(this.magicNumber);
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new StrengthPower(abstractMonster,1)));
        addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new VulnerablePower(abstractMonster,1,false)));
    }
}
