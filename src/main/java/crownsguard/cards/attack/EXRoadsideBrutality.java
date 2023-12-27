package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.BludgeoningDamage;
import crownsguard.damage.HeatActionDamage;
import crownsguard.damage.HeavyDamage;
import crownsguard.util.CardStats;

public class EXRoadsideBrutality extends BaseCard {

    public static final String ID = makeID(EXRoadsideBrutality.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int HEAT_COST = 5;
    public EXRoadsideBrutality() {
        super(ID, info,true);

        setDamage(DAMAGE,UPG_DAMAGE);
        setExhaust(true);
        setSelfRetain(true);
        this.baseMagicNumber = HEAT_COST;
        this.magicNumber = baseMagicNumber;

//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
        DamageModifierManager.addModifier(this, new HeatActionDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        ((PlaywrightCharacter)p).decreaseHeatWhenAttack(this.magicNumber);
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
            return false;

        // Not my mod
        if (!(p instanceof PlaywrightCharacter)) {
            canUse = false;
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }

        // Not enough Heat
        if ((p instanceof PlaywrightCharacter) && ((PlaywrightCharacter) p).heat < 5){
            canUse = false;
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        }

        return canUse;
    }
}
