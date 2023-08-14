package org.example.model.GameInfo;

import org.example.enums.FloorType;
import org.example.enums.StoreGood;
import org.example.enums.TroopTypes;
import org.example.model.DataBase;

import java.util.ArrayList;

public class Store extends Building {

    private final Integer maxCap;
    private Integer capacity;
    private final ArrayList<Good> goodList = new ArrayList<>();

    public Store(Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        if (type.equals(StoreGood.Granary.name())) {
            this.maxCap = 100;
            goodList.addAll(StoreGood.Granary.getGoodList());
        } else if (type.equals(StoreGood.StockPile.name())) {
            this.maxCap = 200;
            goodList.addAll(StoreGood.StockPile.getGoodList());
        } else if (type.equals(StoreGood.Armoury.name())) {
            this.maxCap = 50;
            goodList.addAll(StoreGood.Armoury.getGoodList());
        } else if (type.equals(StoreGood.Stable.name())) {
            this.maxCap = 5;
            goodList.addAll(StoreGood.Stable.getGoodList());
        } else {
            this.maxCap = 0;
            return;
        }
        capacity = this.maxCap;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getMaxCap() {
        return maxCap;
    }

    public ArrayList<Good> getGoodList() {
        return goodList;
    }

}
