package org.example.model.GameInfo;

public class EngineerBuilding {
    private Government owner;
    private String name;
    private Integer price;
    private Integer hitpoint;
    private Integer destroyingPower;

    public EngineerBuilding(Government owner, String name, Integer price, Integer maxHitpoint, Integer destroyingPower) {
        this.owner = owner;
        this.name = name;
        this.price = price;
        this.hitpoint = maxHitpoint;
        this.destroyingPower = destroyingPower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDestroyingPower() {
        return destroyingPower;
    }

    public Government getOwner() {
        return owner;
    }

}
