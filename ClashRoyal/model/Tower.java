package model;
public class Tower {

    private String towerName;
    private int hitpoint;
    private User towerOwner;
    private int towerFightPower;

    public Tower(String towerName, User towerOwner) {
        setTowerName(towerName);
        setTowerOwner(towerOwner);
        setTheTowerMaxHitpoint();
        setTowerFightPower();
    }

    public void setTheTowerMaxHitpoint() {
        int ownerLevel = this.towerOwner.getLevel();
        if (this.towerName.equals("middle")) {
            this.hitpoint = 3600 * ownerLevel;
        } else {
            this.hitpoint = 2500 * ownerLevel;
        }
    }
    

    private void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    private void setTowerOwner(User towerOwner) {
        this.towerOwner = towerOwner;
    }

    public void setTowerFightPower() {
        int ownerLevel = this.towerOwner.getLevel();
        this.towerFightPower = 500 * ownerLevel;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public int getTowerFightPower() {
        return towerFightPower;
    }

}
