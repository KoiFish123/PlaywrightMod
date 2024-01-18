package crownsguard.potions;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static crownsguard.PlaywrightMod.makeID;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import crownsguard.character.PlaywrightCharacter;
import crownsguard.powers.DrunkPower;


public class PremiumMalt extends BasePotion{
    public static final String ID = makeID(PremiumMalt.class.getSimpleName());
    private static final Color LIQUID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 0, 255);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 0, 255);

    public PremiumMalt() {
        super(ID, 4, PotionRarity.UNCOMMON, PotionSize.BOTTLE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + potency/2 + potionStrings.DESCRIPTIONS[2];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (player instanceof PlaywrightCharacter){
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                addToBot(new HealAction(player, player, potency));
                addToBot(new ApplyPowerAction(player, player, new DrunkPower(player, potency/2), potency/2));
            }
        }
    }
}
