package org.example.enums;

public enum Mines {
    IronMine("IronMine", 10, "iron", "IronGround", 0, 0, 0),
    StoneMine("StoneMine", 20, "stone", "StoneGround", 0, 0, 0),
    WoodCutter("WoodCutter", 35, "wood", "", 0, 0, 0),
    OxTether("OxTether", 0, "", "", 0, 0, 0),
    StockPile("StockPile", 0, "", "", 0, 0, 0),
    armourer("armourer", 3, "LeatherArmour", "", 0, 0, 2),
    blacksmith("blacksmith", 2, "Mace", "", 0, 0, 2),
    Fletcher("Fletcher", 4, "Bow", "", 3, 0, 0),
    Poleturner("Poleturner", 3, "Spear", "", 0, 0, 1),
    Stable("Stable", 2, "Horse", "",0,0,0),

    ;
    private String name;
    private String productionName;
    private Integer productionRate;
    private String validFloorType;
    private Integer usedWoodPerTurn;
    private Integer usedStonePerTurn;
    private Integer usedIronPerTurn;

    Mines(String name, Integer productionRate, String productionName, String validFloorType, Integer usedWoodPerTurn, Integer usedStonePerTurn, Integer usedIronPerTurn) {
        this.name = name;
        this.productionName = productionName;
        this.productionRate = productionRate;
        this.validFloorType = validFloorType;
        this.usedWoodPerTurn = usedWoodPerTurn;
        this.usedStonePerTurn = usedStonePerTurn;
        this.usedIronPerTurn = usedIronPerTurn;
    }

    public String getName() {
        return name;
    }

    public String getProductionName() {
        return productionName;
    }

    public Integer getProductionRate() {
        return productionRate;
    }

    public String getValidFloorType() {
        return validFloorType;
    }

    public Integer getUsedWoodPerTurn() {
        return usedWoodPerTurn;
    }

    public Integer getUsedStonePerTurn() {
        return usedStonePerTurn;
    }

    public Integer getUsedIronPerTurn() {
        return usedIronPerTurn;
    }
}
