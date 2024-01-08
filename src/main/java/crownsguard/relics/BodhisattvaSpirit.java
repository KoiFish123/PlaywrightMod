package crownsguard.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.character.crownsguard.TheCrownsguard;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.CrownsguardMod.makeID;

public class BodhisattvaSpirit extends BaseRelic{
    private static final String NAME = "BodhisattvaSpirit";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    public BodhisattvaSpirit() {
        super(ID, NAME, TheCrownsguard.Enums.COLOR_ORANGE, RARITY, SOUND);
    }


    @Override
    public void atTurnStart() {
        if (player instanceof PlaywrightCharacter) {
            this.flash();
            addToBot(new GainBlockAction(player, player, ((PlaywrightCharacter) player).exCharge * 2));
            addToBot(new HealAction(player,player,((PlaywrightCharacter) player).exCharge));
            AbstractDungeon.actionManager.addToBottom(
                    new AbstractGameAction() {
                        @Override
                        public void update() {
                            ((PlaywrightCharacter) player).decreaseEXChargeWhenAttack(((PlaywrightCharacter) player).exCharge);
                        }
                    }
            );
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
