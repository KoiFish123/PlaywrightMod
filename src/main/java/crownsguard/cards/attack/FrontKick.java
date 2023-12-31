package crownsguard.cards.attack;

import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.TheCrownsguard;
import crownsguard.damage.LightDamage;
import crownsguard.stances.BruiserStance;
import crownsguard.util.CardStats;


public class FrontKick extends BaseCard {
    public static final String ID = makeID(FrontKick.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.ATTACK,
            CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 3;

    public FrontKick() {
        super(ID, info,true);
        DamageModifierManager.addModifier(this, new LightDamage());
        setDamage(DAMAGE,UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ChangeStanceAction(new BruiserStance()));
    }
}
