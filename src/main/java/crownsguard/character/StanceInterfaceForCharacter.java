package crownsguard.character;

public interface StanceInterfaceForCharacter {
    String getPowerTypeForCurrentStance(String id); // Gain power depend on current stance

    void heatColorChange(String id);    // Change power when stance change
}
