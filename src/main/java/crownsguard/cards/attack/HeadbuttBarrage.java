package crownsguard.cards.attack;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.BaseCard;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.HeadbuttBarrageAction;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class HeadbuttBarrage extends BaseCard {
    public static final String ID = makeID(HeadbuttBarrage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            AbstractCard.CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            -1
    );

    private static final int DAMAGE = 8;

    public HeadbuttBarrage() {
        super(ID, info, true);
        setDamage(DAMAGE);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HeadbuttBarrageAction(this, p, this.damage, this.freeToPlayOnce, this.energyOnUse));

        AbstractDungeon.actionManager.addToBottom(
                new AbstractGameAction() {
                    @Override
                    public void update() {
                        this.isDone = true;
                        addToBot(new TalkAction(p,languagePack.getCardStrings(cardID).EXTENDED_DESCRIPTION[0], 1.2F, 1.2F));
                    }
                }
        );
    }
}
