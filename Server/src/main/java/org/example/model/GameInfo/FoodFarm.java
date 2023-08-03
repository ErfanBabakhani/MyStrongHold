package org.example.model.GameInfo;

public class FoodFarm extends Building {
    private Integer productionNumber;
    private String productionName;

    public FoodFarm(Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood, Integer productionNumber, String productionName) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        this.productionNumber = productionNumber;
        this.productionName = productionName;
    }

    public Integer getProductionNumber() {
        return productionNumber;
    }

    public String getProductionName() {
        return productionName;
    }
}
