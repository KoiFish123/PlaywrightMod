package crownsguard.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Reguard extends BaseCard implements ReactionToDamageCard {
    public static final String ID = makeID(Reguard.class.getSimpleName());

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 5;

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -2
    );

    public Reguard(){
        super(ID,info);
        CardModifierManager.addModifier(this,new ReactionCardMod());
        setSelfRetain(true);
        setBlock(BLOCK,UPG_BLOCK);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = languagePack.getCardStrings(cardID).UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reguard();
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {
        if (player.currentBlock > 0) {
            if (amount >= player.currentBlock) {
                addToTop(new DiscardSpecificCardAction(this));
                addToTop(new GainBlockAction(player, BLOCK));
                return amount;
            }
        }
        return amount;
    }
}
