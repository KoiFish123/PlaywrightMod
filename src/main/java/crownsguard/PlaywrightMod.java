package crownsguard;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import crownsguard.cards.*;
import crownsguard.cards.attack.EmptyAttack;
import crownsguard.cards.power.EmptyPower;
import crownsguard.cards.reactionInterface.ReactionToDamageCard;
import crownsguard.cards.reactionInterface.ReactionToDrawCard;
import crownsguard.cards.reactionInterface.ReactionToExhaustCard;
import crownsguard.cards.reactionInterface.ReactionToPowerCard;
import crownsguard.cards.skill.EmptySkill;
import crownsguard.character.crownsguard.TheCrownsguard;
import crownsguard.damage.mainDamage.QuickDamage;
import crownsguard.potions.BasePotion;
import crownsguard.relics.BaseRelic;
import crownsguard.util.GeneralUtils;
import crownsguard.util.KeywordInfo;
import crownsguard.util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

@SpireInitializer
public class PlaywrightMod implements
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        PostDrawSubscriber,
        PostExhaustSubscriber,
        EditKeywordsSubscriber,
        OnPlayerDamagedSubscriber,
PostPowerApplySubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this

    static {
        loadModInfo();
    }

    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.
    private static final String resourcesFolder = "crownsguard";
    private static final String BG_ATTACK = characterPath("cardback/Crownsguard/bg_attack.png");
    private static final String BG_ATTACK_P = characterPath("cardback/Crownsguard/bg_attack_p.png");
    private static final String BG_SKILL = characterPath("cardback/Crownsguard/bg_skill.png");
    private static final String BG_SKILL_P = characterPath("cardback/Crownsguard/bg_skill_p.png");
    private static final String BG_POWER = characterPath("cardback/Crownsguard/bg_power.png");
    private static final String BG_POWER_P = characterPath("cardback/Crownsguard/bg_power_p.png");
    private static final String ENERGY_ORB = characterPath("cardback/Crownsguard/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/Crownsguard/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/Crownsguard/small_orb.png");
    private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
    private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

    private static final Color cardColor = new Color(255f / 255f, 165f / 255f, 0f / 255f, 1f);

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        logger.info("========================= Initializing Mod. =========================");
        new PlaywrightMod();

        BaseMod.addColor(TheCrownsguard.Enums.COLOR_ORANGE, cardColor, BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                SMALL_ORB);
        logger.info("========================= /Mod Initialized/ =========================");
    }

    public PlaywrightMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {

        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));

        // we'll make a separate static method to register all the potions in, and just call that method here
        registerPotions();

        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, null);
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                localizationPath(lang, "StanceString.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "TutorialStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            } catch (Exception e) {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }

    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }

    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }

    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }


    //This determines the mod's ID based on information stored by ModTheSpire.
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(PlaywrightMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheCrownsguard(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT, TheCrownsguard.Enums.THE_CROWNSGUARD);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards

        new AutoAdd(modID)
                .packageFilter(EmptyAttack.class)
                .setDefaultSeen(true)
                .cards();

        new AutoAdd(modID)
                .packageFilter(EmptyPower.class)
                .setDefaultSeen(true)
                .cards();

        new AutoAdd(modID)
                .packageFilter(EmptySkill.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
//                    if (info.seen)
//                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    public static void registerPotions() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BasePotion.class) //In the same package as this class
                .any(BasePotion.class, (info, potion) -> { //Run this code for any classes that extend this class
                    //These three null parameters are colors.
                    //If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    //This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    //playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }

    @Override
    public int receiveOnPlayerDamaged(int amount, DamageInfo damageInfo) {
        if (damageInfo.owner instanceof AbstractMonster && damageInfo.type == DamageInfo.DamageType.NORMAL) {
            for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(damageInfo)) {
                if (mod instanceof QuickDamage) {
                    return amount;
                }
            }
            for (AbstractCard card : player.hand.group) {
                if (card instanceof ReactionToDamageCard) {
                    amount = ((ReactionToDamageCard) card).onPlayerDamaged(amount, damageInfo);
                    break;
                }
            }
        }
        return amount;
    }

    @Override
    public void receivePostDraw(AbstractCard abstractCard) {
        for (AbstractCard card : player.hand.group) {
            if (card instanceof ReactionToDrawCard) {
                ((ReactionToDrawCard) card).AfterCardDrawn(abstractCard);
                break;
            }
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        for (AbstractCard card : player.hand.group) {
            if (card instanceof ReactionToExhaustCard) {
                ((ReactionToExhaustCard) card).AfterCardExhausted(abstractCard);
                break;
            }
        }
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        for (AbstractCard card : player.hand.group) {
            if (card instanceof ReactionToPowerCard) {
                if (target == player)
                    ((ReactionToPowerCard) card).onPowerAppliedToPlayer(abstractPower,target,source);
                if (target != player)
                    ((ReactionToPowerCard) card).onPowerAppliedToMonster(abstractPower,target,source);
                break;
            }
        }
    }
}
