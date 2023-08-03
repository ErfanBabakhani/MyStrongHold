package org.example.model.GameInfo;

public class Troop extends People {
    private String state = "standing";
    private Integer power;
    private Integer hitpoint;
    private Integer speed;
    private String troopGroup;
    private Home home;
    private boolean isInBord = false;

    public void setState(String state) {
        this.state = state;
    }

    public Troop(String type, String troopGroup, Government owner, Integer power, Integer hitpoint, Integer speed) {
        super(type, owner);
        this.power = power;
        this.hitpoint = hitpoint;
        this.troopGroup = troopGroup;
        setSpeed(speed);
    }

    private void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setInBord(boolean inBord) {
        isInBord = inBord;
    }

    public Integer getPower() {
        return power;
    }

    public Integer getHitpoint() {
        return hitpoint;
    }

    public void setHitpoint(Integer hitpoint) {
        this.hitpoint = hitpoint;
    }

    public String getState() {
        return state;
    }
}
