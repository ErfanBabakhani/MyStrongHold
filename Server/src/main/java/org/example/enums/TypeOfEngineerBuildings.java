package org.example.enums;

public enum TypeOfEngineerBuildings {
    Shield("Shield", 20, 1, 150, 0),
    DejKoob("DejKoob", 30, 4, 70, 40),
    BorjeMohasere("BorjeMohasere", 40, 4, 70, 40),
    MovableManjenigh("MovableManjenigh", 25, 2, 50, 0),
    StopManjenigh("StopManjenigh", 30, 3, 65, 75),
    FireStoneDropper("FireStoneDropper", 30, 2, 50, 40),
    ;
    private final String name;
    private final Integer price;
    private final Integer neededEngineers;
    private final Integer maxHitpoint;
    private final Integer destroyingPower;

    TypeOfEngineerBuildings(String name, Integer price, Integer neededEngineers, Integer maxHitpoint, Integer destroyingPower) {
        this.name = name;
        this.price = price;
        this.neededEngineers = neededEngineers;
        this.maxHitpoint = maxHitpoint;
        this.destroyingPower = destroyingPower;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getNeededEngineers() {
        return neededEngineers;
    }

    public Integer getMaxHitpoint() {
        return maxHitpoint;
    }

    public Integer getDestroyingPower() {
        return destroyingPower;
    }
}
