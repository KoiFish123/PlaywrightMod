package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.EXRelentlessBlowAction;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.damage.EXActionDamage;
import crownsguard.damage.LightDamage;
import crownsguard.util.CardStats;

public class EXRelentlessBlow extends BaseCard {
    public static final String ID = makeID(EXRelentlessBlow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 8;
    private static final int EX_CHARGE_COST = 5;

    public EXRelentlessBlow() {
        super(ID, info,true);

        setDamage(DAMAGE);
        setExhaust(true);
        this.baseMagicNumber = EX_CHARGE_COST;
        this.magicNumber = baseMagicNumber;

//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
        DamageModifierManager.addModifier(this, new EXActionDamage());
        DamageModifierManager.addModifier(this, new LightDamage());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonusFromEXGauge = 0;

        if (!(p instanceof PlaywrightCharacter)) {
            addToBot(new EXRelentlessBlowAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, bonusFromEXGauge));
        }

        if (p instanceof PlaywrightCharacter) {
            if (upgraded)
                bonusFromEXGauge = ((PlaywrightCharacter) p).exCharge / 3;
            else {
                bonusFromEXGauge = ((PlaywrightCharacter) p).exCharge / 5;
            }
            addToBot(new EXRelentlessBlowAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, bonusFromEXGauge));
            ((PlaywrightCharacter)p).decreaseEXChargeWhenAttack(((PlaywrightCharacter) p).exCharge);
        }
    }
}
