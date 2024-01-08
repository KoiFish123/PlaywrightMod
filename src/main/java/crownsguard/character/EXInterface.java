package crownsguard.character;

public interface EXInterface {

    /*
    Directly fill the EX Gauge through means other than attacking (card, relic, etc.)
    [X] No EX Gauge gain while in EX Boost
    [X] Update Visual and Power
     */
    void increaseEXCharge(int num);

    /*
    Fill the EX Gauge by attacking
    [X] No EX Gauge gain while in EX Boost
    [X] Update Visual and Power
     */
    void increaseEXChargeThroughAttack(int num);

    /*
    Directly decrease the EX Gauge through means other than attacking/EX Action (card, relic, etc.)
    [X] Update Visual and Power
     */
    void decreaseEXCharge(int num);

    /*
    Directly decrease the EX Gauge by attacking (EX Action)
    [X] Update Visual and Power
    [X] Yellow Dragon Spirit: Heals 1 HP you when you use EX Action
    [ ] Cruising For A Bruising: While in Bruiser stance, EX Actions cost 1 less EX Gauge to play.
     */
    void decreaseEXChargeWhenAttack(int num);

    /*
    Directly decrease the EX Gauge by attacking (EX Action)
    [X] Update Visual and Power
    [X] While in EX Boost, getting attacked will not decrease the gauge
    [ ] Cruising For A Bruising: While in Bruiser stance, EX Actions cost 1 less EX Gauge to play.
     */
    void decreaseEXChargeWhenAttacked(int num);

    /*
    EX Gauge drains at start of turn while in EX Boost
    Note: Unlike others decreaseEXGauge, decreasing the EX Gauge this way will not reduce power gain from having EX Gauge
    It's hard to explain in word, so an example:
    - As `The Crownsguard`, while you have 10 EX Gauge and is in Bruiser stance, you have +2 Strength
    - If you enter EX Boost, you should still have that +2, even if the gauge go below 10.
    - The bonus adjust itself when you exit EX Boost. If you have 6 EX Gauge when you exit, your Strength bonus becomes +1
    [X] Update Visual and Power using `updateEXGaugeAfterExitEXBoost`
 */
    void decreaseEXChargeFromEXBoost(int exGaugeOnActivation);

    void updateEXGauge(int num);

    void updateEXGaugeAfterExitEXBoost(int exGaugeOnActivation);

    /*
    When you change stance, you want to change the bonus you get to the one from the new stance.
    This method transfer both bonus type and intensity.
     */
    void transferPowerOnStanceChange(String newPowerIncrease);

}
