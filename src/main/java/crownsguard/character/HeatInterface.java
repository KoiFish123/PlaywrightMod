package crownsguard.character;

public interface HeatInterface {

    /*
    Directly increase Heat through means other than attacking (card, relic, etc.)
    [X] No heat gain while in Extreme Heat Mode
    [X] Update Visual and Power
     */
    void increaseHeat(int num);

    /*
    Increase Heat by attacking
    [X] No heat gain while in Extreme Heat Mode
    [X] Update Visual and Power
     */
    void increaseHeatThroughAttack(int num);

    /*
    Directly decrease Heat through means other than attacking/Heat Action (card, relic, etc.)
    [X] Update Visual and Power
     */
    void decreaseHeat(int num);

    /*
    Directly decrease Heat by attacking (Heat Action)
    [X] Update Visual and Power
    [X] Yellow Dragon Spirit: Heals 1 HP you when you use Heat Action
    [ ] Cruising For A Bruising: While in Bruiser stance, Heat Actions cost 1 less Heat to play.
     */
    void decreaseHeatWhenAttack(int num);

    /*
    Directly decrease Heat by attacking (Heat Action)
    [X] Update Visual and Power
    [X] While in Extreme Heat Mode, getting attacked will not decrease the gauge
    [ ] Cruising For A Bruising: While in Bruiser stance, Heat Actions cost 1 less Heat to play.
     */
    void decreaseHeatWhenAttacked(int num);

    /*
    Heat drains at start of turn while in Extreme Heat Mode
    Note: Unlike others decreaseHeat, decreasing Heat this way will not reduce power gain from having heat
    It's hard to explain in word, so an example:
    - As The Crownsguard, while you have 10 Heat and is in Bruiser stance, you have +2 Strength
    - If you enter Extreme Heat Mode, you should still have that +2, even if the gauge go below 10.
    - The bonus adjust itself when you exit the mode. If you have 6 Heat when you exit, your Strength bonus becomes +1
    [X] Update Visual and Power using `updateHeatAfterExitExtremeHeat`
 */
    void decreaseHeatFromExtremeHeat(int heatOnActivation);

    void updateHeat(int num);

    void updateHeatAfterExitExtremeHeat(int heatOnActivation);

    /*
    When you change stance, you want to change the bonus you get to the one from the new stance.
    This method transfer both bonus type and intensity.
     */
    void transferPowerOnStanceChange(String newPowerIncrease);

}
