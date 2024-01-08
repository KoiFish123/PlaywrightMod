package crownsguard.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.stances.BruiserStance;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class Repel extends BaseCard implements ReactionToDamageCard {
    public static final String ID = makeID(Repel.class.getSimpleName());

    private static final int REPEL_POWER = 6;
    private static final int UPG_REPEL_POWER = 4;
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -2
    );

    public Repel(){
        super(ID,info);
        CardModifierManager.addModifier(this,new ReactionCardMod());
        setSelfRetain(true);
        this.baseMagicNumber = REPEL_POWER;
        setDamage(REPEL_POWER, UPG_REPEL_POWER);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {
        if (amount > player.currentBlock) {
            calculateCardDamage((AbstractMonster)info.owner);
            if (amount-player.currentBlock <= this.damage){
                addToTop(new DiscardSpecificCardAction(this));
                if (player.stance.ID.equals(BruiserStance.STANCE_ID))
                    addToTop(new ApplyPowerAction(info.owner, player,new EnergizedPower(player,1)));
                return 0;
            }
        }
        return amount;
    }
}
