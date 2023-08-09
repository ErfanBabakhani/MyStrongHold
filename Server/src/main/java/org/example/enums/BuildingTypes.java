package org.example.enums;

public enum BuildingTypes {
    IronMine(80, "IronMine", 10, "iron", 3, 0, 20, 0, 0, 0, 0, 0),
    StoneMine(120, "StoneMine", 20, "Stone", 3, 0, 0, 0, 0, 0, 0, 0),
    WoodCutter(50, "WoodCutter", 35, "Wood", 1, 0, 0, 0, 0, 0, 0, 0),
    OxTether(20, "OxTether", 0, "", 1, 0, 5, 0, 0, 0, 0, 0),
    StockPile(100, "StockPile", 0, "", 0, 0, 0, 0, 0, 0, 0, 0),
    armourer(50, "armourer", 3, "LeatherArmour", 1, 0, 20, 100, 0, 0, 0, 0),
    blacksmith(50, "blacksmith", 2, "Mace", 1, 0, 20, 100, 0, 0, 0, 0),
    Fletcher(50, "Fletcher", 4, "Bow", 0, 0, 20, 100, 0, 0, 0, 0),
    Poleturner(50, "Poleturner", 3, "Spear", 1, 0, 10, 0, 0, 0, 0, 0),
    Step(15, "Step", 0, "", 0, 0, 0, 0, 0, 0, 0, 0),
    Wall(30, "Wall", 0, "", 0, 0, 0, 0, 0, 0, 0, 0),
    SmallStoneGatehouse(200, "SmallStoneGatehouse", 0, "", 0, 0, 0, 0, 0, 0, 0, 0),
    BigStoneGatehouse(200, "BigStoneGatehouse", 0, "", 0, 20, 0, 0, 0, 0, 0, 0),
    Drawbridge(150, "Drawbridge", 0, "", 0, 0, 10, 0, 0, 0, 0, 0),
    LookoutTower(100, "LookoutTower", 0, "", 0, 10, 0, 0, 40, 40, 0, 0),
    PerimeterTower(100, "PerimeterTower", 0, "", 0, 10, 0, 0, 5, 5, 0, 0),
    DefensiveTower(80, "DefensiveTower", 0, "", 0, 15, 0, 0, 7, 7, 0, 0),
    SquareTower(80, "SquareTower", 0, "", 0, 35, 0, 0, 10, 10, 0, 0),
    CircleTower(75, "CircleTower", 0, "", 0, 40, 0, 0, 0, 0, 0, 0),
    Armoury(0, "Armoury", 0, "", 0, 0, 5, 0, 0, 0, 0, 0),
    Barrack(100, "Barrack", 0, "", 0, 15, 0, 50, 0, 0, 0, 0),
    MercenaryPost(100, "MercenaryPost", 0, "", 0, 0, 10, 30, 0, 0, 0, 0),
    EngineerGuild(100, "EngineerGuild", 0, "", 0, 0, 10, 100, 0, 0, 0, 0),
    KillingPit(40, "KillingPit", 0, "", 0, 0, 6, 0, 0, 0, 5, 0),
    OilSmelter(150, "OilSmelter", 0, "", 1, 0, 0, 100, 0, 0, 0, 0),
    PitchDitch(0, "PitchDitch", 0, "", 0, 0, 0, 0, 0, 0, 0, 0),
    CagedWarDogs(30, "CagedWarDogs", 0, "", 0, 0, 10, 100, 0, 0, 0, 0),
    SiegeTent(70, "SiegeTent", 0, "", 1, 0, 0, 0, 0, 0, 0, 0),
    Stable(150, "Stable", 2, "Horse", 0, 0, 0, 400, 0, 0, 0, 0),
    //    //FoodFarm
    Mill(40, "Mill", 10, "flour", 3, 0, 20, 0, 0, 0, 0, 0),
    Baker(60, "Baker", 5, "Bread", 1, 0, 10, 0, 0, 0, 0, 0),
    Brewery(40, "Brewery", 3, "", 1, 0, 10, 0, 0, 0, 0, 0),
    Granary(50, "Granary", 0, "", 0, 0, 5, 0, 0, 0, 0, 0),
    AppleGarden(50, "AppleGarden", 15, "Apple", 1, 0, 5, 0, 0, 0, 0, 0),
    DairyProducts(50, "DairyProducts", 10, "Cheese", 1, 0, 10, 0, 0, 0, 0, 0),
    BarleyField(50, "BarleyField", 0, "", 1, 0, 15, 0, 0, 0, 0, 0),
    Hunt(50, "Hunt", 5, "Meat", 1, 0, 5, 0, 0, 0, 0, 0),
    WheatField(30, "WheatField", 8, "wheat", 1, 0, 15, 0, 0, 0, 0, 0),
    //    //TownBuilding
    Inn(40, "Inn", 0, "", 1, 0, 20, 100, 0, 0, 0, 0),
    Hovel(40, "Hovel", 0, "", 0, 6, 0, 0, 0, 0, 0, 0),
    Church(100, "Church", 0, "", 0, 0, 0, 250, 0, 0, 0, 2),
    Cathedral(200, "Cathedral", 0, "", 0, 0, 0, 1000, 0, 0, 0, 2),
    ;

    BuildingTypes(Integer maxHitpoint, String type, Integer productionAmount, String productionName, Integer neededWorkers, Integer neededStone, Integer neededWood, Integer price, Integer fireRange, Integer defendRange, Integer damageAmount, Integer popularityRate) {
        this.maxHitpoint = maxHitpoint;
        this.type = type;
        this.productionAmount = productionAmount;
        this.productionName = productionName;
        this.neededWorkers = neededWorkers;
        this.neededStone = neededStone;
        this.neededWood = neededWood;
        this.price = price;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.damageAmount = damageAmount;
        this.popularityRate = popularityRate;
    }

    private Integer maxHitpoint;
    private String type;
    private Integer productionAmount;
    private String productionName;
    private Integer neededWorkers;
    private Integer neededStone;
    private Integer neededWood;
    private Integer price;
    private Integer fireRange;
    private Integer defendRange;
    private Integer damageAmount;
    private Integer popularityRate;

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
