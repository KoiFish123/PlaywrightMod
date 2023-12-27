package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.BludgeoningDamage;
import crownsguard.damage.HeavyDamage;
import crownsguard.powers.RushCombo;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Finisher extends BaseCard {

    public static final String ID = makeID(Finisher.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );



    private static final int DAMAGE = 8;

    AbstractCard c = new DoubleFinisher();

    public Finisher() {
        super(ID, info, true);
        setDamage(DAMAGE);
        this.cardsToPreview = c;
//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
        DamageModifierManager.addModifier(this, new HeavyDamage());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            c.upgrade();
            this.cardsToPreview = c;
            upgradeName();
            rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (p instanceof TheCrownsguard && p.hasPower(RushCombo.POWER_ID))
            addToTop(new GainEnergyAction(p.getPower(RushCombo.POWER_ID).amount));
        addToBot(new MakeTempCardInHandAction(cardsToPreview, true));
    }
}