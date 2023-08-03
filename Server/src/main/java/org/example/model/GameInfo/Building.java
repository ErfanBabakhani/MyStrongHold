package org.example.model.GameInfo;

public class Building {
    private Integer hitpoint;
    private Government owner;
    private final String type;
    private final Integer price;
    private final Integer maxHitpoint;
    private final Integer needWorkers;
    private Integer activeWorkers = 0;
    private final Integer neededStone;
    private final Integer neededWood;

    public Building(Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood) {
        this.hitpoint = maxHitpoint;
        this.owner = owner;
        this.type = type;
        this.maxHitpoint = maxHitpoint;
        this.needWorkers = needWorkers;
        this.price = price;
        this.neededStone = neededStone;
        this.neededWood = neededWood;
    }

    public void setHitpoint(Integer hitpoint) {
        this.hitpoint = hitpoint;
    }

    public Integer getHitpoint() {
        return hitpoint;
    }

    public String getType() {
        return type;
    }

    public Integer getMaxHitpoint() {
        return maxHitpoint;
    }

    public Government getOwner() {
        return owner;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getNeededStone() {
        return neededStone;
    }
}
