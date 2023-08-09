package org.example.enums;

import org.example.model.DataBase;
import org.example.model.GameInfo.Good;

import java.util.ArrayList;

public enum StoreGood {


    Granary(),

    StockPile(),
    Armoury(),
    Stable();

    static {
        Granary.goodList.add(new Good("Apple", 2));
        Granary.goodList.add(new Good("Cheese", 3));
        Granary.goodList.add(new Good("Meat", 4));

        StockPile.goodList.add(new Good("Wood", 2));
        StockPile.goodList.add(new Good("Stone", 3));
        StockPile.goodList.add(new Good("Iron", 4));

        Armoury.goodList.add(new Good("Bow", 2));
        Armoury.goodList.add(new Good("Crossbow", 3));
        Armoury.goodList.add(new Good("LeatherArmour", 4));
        Armoury.goodList.add(new Good("Spear", 2));
        Armoury.goodList.add(new Good("Pike", 3));
        Armoury.goodList.add(new Good("MetalArmour", 5));
        Armoury.goodList.add(new Good("Mace", 4));
        Armoury.goodList.add(new Good("Sword", 4));

        Stable.goodList.add(new Good("Horse", 10));
    }

    StoreGood() {
        this.goodList = new ArrayList<>();
    }

    private ArrayList<Good> goodList;

    public ArrayList<Good> getGoodList() {
        return goodList;
    }
}
