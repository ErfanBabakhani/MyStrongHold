package org.example.model.GameInfo;

import java.util.ArrayList;

public class Home {
    private Government owner;
    private Integer x;
    private Integer y;
    private boolean isOilExist = false;
    private boolean isTunnelCreated = false;
    private boolean isDitchCreated = false;
    private String typeOfFloor;
    private Building building;
    private Tree tree;
    private String Rock;
    private ArrayList<Troop> troops = new ArrayList<>();
    private EngineerBuilding engineerBuilding;

    public Home(Integer x, Integer y, String typeOfFloor) {
        this.x = x;
        this.y = y;
        this.typeOfFloor = typeOfFloor;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }


    public String getTypeOfFloor() {
        return typeOfFloor;
    }

    public Building getBuilding() {
        return building;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setTypeOfFloor(String typeOfFloor) {
        this.typeOfFloor = typeOfFloor;
    }

    public void setTroops(ArrayList<Troop> troops) {
        this.troops = troops;
    }

    public void addTroops(ArrayList<Troop> troops) {
        this.troops.addAll(troops);
    }

    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }


    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Tree getTree() {
        return tree;
    }

    public Government getOwner() {
        return owner;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public boolean isOilExist() {
        return isOilExist;
    }

    public void setOilExist(boolean oilExist) {
        isOilExist = oilExist;
    }

    public boolean isTunnelCreated() {
        return isTunnelCreated;
    }

    public void setTunnelCreated(boolean tunnelCreated) {
        isTunnelCreated = tunnelCreated;
    }

    public void setRock(String rock) {
        Rock = rock;
    }

    public EngineerBuilding getEngineerBuilding() {
        return engineerBuilding;
    }

    public void setEngineerBuilding(EngineerBuilding engineerBuilding) {
        this.engineerBuilding = engineerBuilding;
    }

    public void setDitchCreated(boolean ditchCreated) {
        isDitchCreated = ditchCreated;
    }
}
