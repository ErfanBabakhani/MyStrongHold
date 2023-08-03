package org.example.enums;

import java.util.ArrayList;

public enum TroopTypes {
    Archer("Archer", 30, 10, 9, 15, new ArrayList<>()),
    CrossbowMen("CrossbowMen", 45, 10, 4, 20, new ArrayList<>()),
    SpearMen("SpearMen", 20, 15, 6, 15, new ArrayList<>()),
    PikeMen("PikeMen", 70, 15, 3, 30, new ArrayList<>()),
    MaceMen("MaceMen", 55, 25, 6, 35, new ArrayList<>()),
    SwordsMen("SwordsMen", 20, 35, 3, 35, new ArrayList<>()),
    Knight("Knight", 70, 40, 3, 40, new ArrayList<>()),
    Tunneler("Tunneler", 10, 20, 8, 25, new ArrayList<>()),
    LadderMen("LadderMen", 10, 0, 9, 20, new ArrayList<>()),
    Engineer("Engineer", 10, 0, 6, 25, new ArrayList<>()),
    BlackMonk("BlackMonk", 30, 15, 3, 20, new ArrayList<>()),
    ArabianBow("ArabianBow", 20, 10, 10, 25, new ArrayList<>()),
    Slaves("Slaves", 7, 4, 10, 10, new ArrayList<>()),
    Slingers("Slingers", 7, 6, 8, 15, new ArrayList<>()),
    Assassins("Assassins", 15, 15, 6, 25, new ArrayList<>()),
    HorseArchers("HorseArchers", 15, 10, 15, 25, new ArrayList<>()),
    ArabianSwordsMen("ArabianSwordsMen", 45, 25, 9, 35, new ArrayList<>()),
    FireThrowers("FireThrowers", 10, 20, 9, 25, new ArrayList<>()),
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
    }

    public String type;
    public int hitpoint;
    public int power;
    public int speed;
    public int price;
    public ArrayList<String> weapons;

    TroopTypes(String type, int hitpoint, int power, int speed, int price, ArrayList<String> weapons) {
        this.type = type;
        this.hitpoint = hitpoint;
        this.power = power;
        this.speed = speed;
        this.price = price;
        this.weapons = weapons;
    }

}
