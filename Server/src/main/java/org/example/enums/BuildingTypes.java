package org.example.enums;

public enum BuildingTypes {
    IronMine(80, "IronMine", 3, 0, 20, 0),
    StoneMine(120, "StoneMine", 3, 0, 0, 0),
    WoodCutter(50, "WoodCutter", 1, 0, 0, 0),
    OxTether(20, "OxTether", 1, 0, 5, 0),
    StockPile(100, "StockPile", 0, 0, 0, 0),
    armourer(50, "armourer", 1, 0, 20, 100),
    blacksmith(50, "blacksmith", 1, 0, 20, 100),
    Fletcher(50, "Fletcher", 0, 0, 20, 100),
    Poleturner(50, "Poleturner", 1, 0, 10, 0),
    Step(15, "Step", 0, 0, 0, 0),
    Wall(30, "Wall", 0, 0, 0, 0),
    SmallStoneGatehouse(200, "SmallStoneGatehouse", 0, 0, 0, 0),
    BigStoneGatehouse(200, "BigStoneGatehouse", 0, 20, 0, 0),
    Drawbridge(150, "Drawbridge", 0, 0, 10, 0),
    LookoutTower(100, "LookoutTower", 0, 10, 0, 0),
    PerimeterTower(100, "PerimeterTower", 0, 10, 0, 0),
    DefensiveTower(80, "DefensiveTower", 0, 15, 0, 0),
    SquareTower(80, "SquareTower", 0, 35, 0, 0),
    CircleTower(75, "CircleTower", 0, 40, 0, 0),
    Armoury(0, "Armoury", 0, 0, 5, 0),
    Barrack(100, "Barrack", 0, 15, 0, 50),
    MercenaryPost(100, "MercenaryPost", 0, 0, 10, 30),
    EngineerGuild(100, "EngineerGuild", 0, 0, 10, 100),
    KillingPit(40, "KillingPit", 0, 0, 6, 0),
    OilSmelter(150, "OilSmelter", 1, 0, 0, 100),
    PitchDitch(0, "PitchDitch", 0, 0, 0, 0),
    CagedWarDogs(30, "CagedWarDogs", 0, 0, 10, 100),
    SiegeTent(70, "SiegeTent", 1, 0, 0, 0),
    Stable(150, "Stable", 0, 0, 0, 400),
    //    //FoodFarm
    Mill(40, "Mill", 3, 0, 20, 0),
    Baker(60, "Baker", 1, 0, 10, 0),
    Brewery(40, "Brewery", 1, 0, 10, 0),
    Granary(50, "Granary", 0, 0, 5, 0),
    AppleGarden(50, "AppleGarden", 1, 0, 5, 0),
    DairyProducts(50, "DairyProducts", 1, 0, 10, 0),
    BarleyField(50, "BarleyField", 1, 0, 15, 0),
    Hunt(50, "Hunt", 1, 0, 5, 0),
    WheatField(30, "WheatField", 1, 0, 15, 0),
    //    //TownBuilding
    Inn(40, "Inn", 1, 0, 20, 100),
    Hovel(40, "Hovel", 0, 6, 0, 0),
    Church(100, "Church", 0, 0, 0, 250),
    Cathedral(200, "Cathedral", 0, 0, 0, 1000),
    ;

    BuildingTypes(Integer maxHitpoint, String type, Integer neededWorkers, Integer neededStone, Integer neededWood, Integer price) {
        this.maxHitpoint = maxHitpoint;
        this.type = type;
        this.neededWorkers = neededWorkers;
        this.neededStone = neededStone;
        this.neededWood = neededWood;
        this.price = price;
    }

    private final Integer maxHitpoint;
    private final String type;
    private final Integer neededWorkers;
    private final Integer neededStone;
    private final Integer neededWood;
    private final Integer price;

    public Integer getMaxHitpoint() {
        return maxHitpoint;
    }

    public String getType() {
        return type;
    }

    public Integer getNeededStone() {
        return neededStone;
    }

    public Integer getNeededWood() {
        return neededWood;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getNeededWorkers() {
        return neededWorkers;
    }
}
