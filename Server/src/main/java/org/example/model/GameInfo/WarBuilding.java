package org.example.model.GameInfo;

public class WarBuilding extends Building {
    protected final Integer powerOfDestroying;
    private final Integer fireRange;
    private final Integer defendRange;

    public WarBuilding(Government owner, String type, Integer maxHitpoint, Integer powerOfDestroying, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood, Integer fireRange, Integer defendRange) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        this.powerOfDestroying = powerOfDestroying;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
    }
}
