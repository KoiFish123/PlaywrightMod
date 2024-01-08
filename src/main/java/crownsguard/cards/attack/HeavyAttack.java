package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.damage.HeavyDamage;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class HeavyAttack extends BaseCard {
    public static final String ID = makeID(HeavyAttack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 4;

    public HeavyAttack() {
        super(ID, info,true);
        setDamage(DAMAGE);
//        DamageModifierManager.addModifier(this, new BludgeoningDamage());
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            DamageModifierManager.addModifier(this, new HeavyDamage());
            rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(
                new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        ((PlaywrightCharacter)p).increaseEXCharge(2);
                    }
                }
        );
    }
}

