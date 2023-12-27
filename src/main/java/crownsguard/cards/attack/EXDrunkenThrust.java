package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.HeatActionDamage;
import crownsguard.damage.HeavyDamage;
import crownsguard.powers.DrunkPower;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class EXDrunkenThrust extends BaseCard {
    public static final String ID = makeID(EXDrunkenThrust.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 10;
    private static final int HEAT_COST = 5;
    public EXDrunkenThrust() {
        super(ID, info,true);

        setDamage(DAMAGE);
        setExhaust(true);
        setSelfRetain(true);
        this.baseMagicNumber = HEAT_COST;
        this.magicNumber = baseMagicNumber;

        DamageModifierManager.addModifier(this, new HeatActionDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;

        if (player.hasPower(DrunkPower.POWER_ID)) {
            if (upgraded)
                this.baseDamage += (player.getPower(DrunkPower.POWER_ID).amount * 3);
            else
                this.baseDamage += (player.getPower(DrunkPower.POWER_ID).amount * 2);
        }

        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified= (this.damage != this.baseDamage);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        ((PlaywrightCharacter)p).decreaseHeatWhenAttack(this.magicNumber);
        addToBot(new RemoveSpecificPowerAction(p,p,DrunkPower.POWER_ID));
    }
}
