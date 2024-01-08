package crownsguard.cards.skill;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import crownsguard.cards.BaseCard;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.potions.PremiumMalt;
import crownsguard.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

/*
This card's purpose is for me to test the 'Premium Malt' potion.
After I am done
Todo: Delete this card
 */
public class ImportPremiumMalt extends BaseCard {
    public static final String ID = makeID(ImportPremiumMalt.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TheCrownsguard.Enums.COLOR_ORANGE,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            0
    );
    public ImportPremiumMalt() {
        super(ID, info,true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < player.potionSlots; i++)
            AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(new PremiumMalt()));
    }
}
