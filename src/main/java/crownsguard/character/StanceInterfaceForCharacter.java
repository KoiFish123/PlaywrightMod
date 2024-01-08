package crownsguard.character;

public interface StanceInterfaceForCharacter {
    String getPowerTypeForCurrentStance(String id); // Change power depend on current stance

    void exGaugeColorChange(String id);    // Change color when stance change
}
