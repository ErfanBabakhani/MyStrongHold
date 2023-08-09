package org.example.enums;

public enum WarBuildings {
    Step("Step", 0, 0, 0),
    Wall("Wall", 0, 0, 0),
    SmallStoneGatehouse("SmallStoneGatehouse", 0, 0, 0),
    BigStoneGatehouse("BigStoneGatehouse", 0, 0, 0),
    Drawbridge("Drawbridge", 0, 0, 0),
    LookoutTower("LookoutTower", 40, 40, 0),
    PerimeterTower("PerimeterTower", 5, 5, 0),
    DefensiveTower("DefensiveTower", 7, 7, 0),
    SquareTower("SquareTower", 10, 10, 0),
    CircleTower("CircleTower", 0, 0, 0),
    //    Armoury("Armoury", 0, 0, 0),
    Barrack("Barrack", 0, 0, 0),
    MercenaryPost("MercenaryPost", 0, 0, 0),
    EngineerGuild("EngineerGuild", 0, 0, 0),
    KillingPit("KillingPit", 0, 0, 5),
    OilSmelter("OilSmelter", 0, 0, 0),
    PitchDitch("PitchDitch", 0, 0, 0),
    CagedWarDogs("CagedWarDogs", 0, 0, 0),
    SiegeTent("SiegeTent", 0, 0, 0),
    ;
    private String name;
    private Integer fireRange;
    private Integer defendRange;
    private Integer powerOfDestroying;

    WarBuildings(String name, Integer fireRange, Integer defendRange, Integer powerOfDestroying) {
        this.name = name;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.powerOfDestroying = powerOfDestroying;
    }

    public String getName() {
        return name;
    }

    public Integer getFireRange() {
        return fireRange;
    }

    public Integer getDefendRange() {
        return defendRange;
    }

    public Integer getPowerOfDestroying() {
        return powerOfDestroying;
    }
}
