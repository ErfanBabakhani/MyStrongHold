package org.example.model.GameInfo;

public class Mine extends Building {
    private Integer productionAmount;
    private String productionName;

    public Mine(Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood, Integer productionAmount, String productionName) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        this.productionAmount = productionAmount;
        this.productionName = productionName;
    }

    public Integer getProductionAmount() {
        return productionAmount;
    }

    public String getProductionName() {
        return productionName;
    }

}
