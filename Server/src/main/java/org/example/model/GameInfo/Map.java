package org.example.model.GameInfo;

import java.util.ArrayList;

public class Map {
    private Building selectedBuilding;
    private Integer xSize;
    private Integer ySize;
    private ArrayList<Home> homes = new ArrayList<>();

    public Map(Integer xSize, Integer ySize, Integer playerNumber) {
        this.xSize = xSize;
        this.ySize = ySize;
        createHomes();
    }

    public ArrayList<Home> getHomes() {
        return homes;
    }

    public Integer getxSize() {
        return xSize;
    }

    public Integer getySize() {
        return ySize;
    }

    private void createHomes() {
        for (int i = 1; i <= this.xSize; i++) {
            for (int j = 1; j <= this.ySize; j++) {
                homes.add(new Home(i, j, "FlatGround"));
            }
        }
    }
}
