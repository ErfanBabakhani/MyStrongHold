package org.example.model.GameInfo;

public class TownBuilding extends Building {
    private final Integer populationIncrease;
    private final Integer popularityIncrease;

    public TownBuilding(Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood, Integer populationIncrease, Integer popularityIncrease) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        this.populationIncrease = populationIncrease;
        this.popularityIncrease = popularityIncrease;
    }

    public Integer getPopulationIncrease() {
        return populationIncrease;
    }

    public Integer getPopularityIncrease() {
        return popularityIncrease;
    }

}
