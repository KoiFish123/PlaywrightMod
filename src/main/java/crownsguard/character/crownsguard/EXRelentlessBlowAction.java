package crownsguard.character.crownsguard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EXRelentlessBlowAction extends AbstractGameAction {
    private boolean freeToPlayOnce;

    private int damage;

    private AbstractPlayer p;

    private AbstractMonster m;

    private DamageInfo.DamageType damageTypeForTurn;

    private int energyOnUse;
    private int bonusFromEXGauge;

    public EXRelentlessBlowAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, int bonusFromEXGauge) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.bonusFromEXGauge = bonusFromEXGauge;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        effect += bonusFromEXGauge;
        if (effect > 0) {
            for (int i = 0; i < effect; i++)
                addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            if (!this.freeToPlayOnce)
                this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
