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
        if (type.equals("Granary")) {
            this.maxCap = 100;
            goodList.addAll(StoreGood.Granary.getGoodList());
        } else if (type.equals("StockPile")) {
            this.maxCap = 200;
            goodList.addAll(StoreGood.StockPile.getGoodList());
        } else if (type.equals("Armoury")) {
            this.maxCap = 50;
            goodList.addAll(StoreGood.Armoury.getGoodList());
        } else if (type.equals("Stable")) {
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

    public static void main(String[] args) {
        Store store = new Store(null, "Granary", 0, 0, 0, 0, 0);
        System.out.println(store.goodList.get(0).getGoodName());
        System.out.println(store.goodList.get(0).getPrice());
        System.out.println(store.capacity);
//        for (int i = 0; i < FloorType.values().length; i++) {
//            System.out.println(FloorType.values()[i]);
//        }
        for (int i = 0; i < TroopTypes.values().length; i++) {
            System.out.println(TroopTypes.values()[i].type + TroopTypes.values()[i].weapons);
        }
        for (int i = 0; i < FloorType.values().length; i++) {
            System.out.println(FloorType.values()[i].name());
        }
    }
}
