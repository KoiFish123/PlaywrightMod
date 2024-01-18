package crownsguard.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import crownsguard.util.TextureLoader;

import static crownsguard.PlaywrightMod.makeID;

public class PlaywrightFlightPower extends BasePower {
    public static final String POWER_ID = makeID(PlaywrightFlightPower.class.getSimpleName());

    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;

    private static final boolean TURN_BASED = true;
    public PlaywrightFlightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.description = DESCRIPTIONS[0];

        String textureID = FlightPower.POWER_ID;

        Texture normalTexture = TextureLoader.getPowerTexture(textureID);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(textureID);

        if (hiDefImage != null) {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        } else if (normalTexture != null) {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount/2 >= AbstractDungeon.player.currentBlock){
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, this,1));
        }
        return damageAmount/2;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this,1));
    }
}
