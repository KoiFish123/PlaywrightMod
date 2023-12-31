package crownsguard.cards.skill;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import crownsguard.cards.cardMod.ReactionCardMod;
import crownsguard.cards.BaseCard;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.stances.TankStance;
import crownsguard.character.TheCrownsguard;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class PerfectGuard extends BaseCard implements ReactionToDamageCard {

    public static final String ID = makeID(PerfectGuard.class.getSimpleName());

    private static final int DEX_SCALE = 1;
    private static final int UPG_DEX_SCALE = 5;

    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -2
    );

    public PerfectGuard(){
        super(ID,info);
        CardModifierManager.addModifier(this,new ReactionCardMod());
        setSelfRetain(true);
        setBlock(DEX_SCALE, UPG_DEX_SCALE);
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // Empty Implementation
    }

    @Override
    protected void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        if (player.hasPower(StrengthPower.POWER_ID))
            this.baseBlock += (player.getPower(StrengthPower.POWER_ID)).amount;

        super.applyPowersToBlock();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    @Override
    public int onPlayerDamaged(int amount, DamageInfo info) {

        if (amount > player.currentBlock) {
            addToTop(new DiscardSpecificCardAction(this));
            amount -= block;

            if (player.hasPower(StrengthPower.POWER_ID))
                amount -= (player.getPower(StrengthPower.POWER_ID)).amount;

            if (player.stance.ID.equals(TankStance.STANCE_ID) && amount == 0)
                addToTop(new ApplyPowerAction(info.owner, player,new WeakPower(info.owner,1,false)));
        }
        return amount;
    }
}
