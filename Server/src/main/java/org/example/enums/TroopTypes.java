package org.example.enums;

import java.util.ArrayList;

public enum TroopTypes {
    Archer("Archer", 30, 10, 9, 15),
    CrossbowMen("CrossbowMen", 45, 10, 4, 20),
    SpearMen("SpearMen", 20, 15, 6, 15),
    PikeMen("PikeMen", 70, 15, 3, 30),
    MaceMen("MaceMen", 55, 25, 6, 35),
    SwordsMen("SwordsMen", 20, 35, 3, 35),
    Knight("Knight", 70, 40, 3, 40),
    Tunneler("Tunneler", 10, 20, 8, 25),
    LadderMen("LadderMen", 10, 0, 9, 20),
    Engineer("Engineer", 10, 0, 6, 25),
    BlackMonk("BlackMonk", 30, 15, 3, 20),
    ArabianBow("ArabianBow", 20, 10, 10, 25),
    Slaves("Slaves", 7, 4, 10, 10),
    Slingers("Slingers", 7, 6, 8, 15),
    Assassins("Assassins", 15, 15, 6, 25),
    HorseArchers("HorseArchers", 15, 10, 15, 25),
    ArabianSwordsMen("ArabianSwordsMen", 45, 25, 9, 35),
    FireThrowers("FireThrowers", 10, 20, 9, 25),
    ;

    static {
        Archer.weapons.add("Bow");
        CrossbowMen.weapons.add("Crossbow");
        CrossbowMen.weapons.add("LeatherArmour");
        SpearMen.weapons.add("Spear");
        PikeMen.weapons.add("Pike");
        PikeMen.weapons.add("MetalArmour");
        MaceMen.weapons.add("Mace");
        MaceMen.weapons.add("MetalArmour");
        SwordsMen.weapons.add("Sword");
        SwordsMen.weapons.add("MetalArmour");
        Knight.weapons.add("Sword");
        Knight.weapons.add("MetalArmour");
        Knight.weapons.add("Horse");
        HorseArchers.weapons.add("Horse");
        HorseArchers.weapons.add("Bow");
    }

    public final String type;

    public final int hitpoint;
    public final int power;
    public final int speed;
    public final int price;
    public final ArrayList<String> weapons;

    TroopTypes(String type, int hitpoint, int power, int speed, int price) {
        this.type = type;
        this.hitpoint = hitpoint;
        this.power = power;
        this.speed = speed;
        this.price = price;
        this.weapons = new ArrayList<>();
    }

}
