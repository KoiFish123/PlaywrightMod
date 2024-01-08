package crownsguard.character.crownsguard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class HeadbuttBarrageAction extends AbstractGameAction {
    public int[] multiDamage;

    private boolean freeToPlayOnce = false;

    private AbstractPlayer p;

    private int energyOnUse = -1;

    private AbstractCard card;

    public HeadbuttBarrageAction(AbstractCard card, AbstractPlayer p, int amount, boolean freeToPlayOnce, int energyOnUse) {
        this.card = card;
        this.amount = amount;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++){
                addToBot(new AttackDamageRandomEnemyAction(card, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
            }
            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
