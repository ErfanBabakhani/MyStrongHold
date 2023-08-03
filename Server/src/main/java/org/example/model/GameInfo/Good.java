package org.example.model.GameInfo;

import java.util.ArrayList;

public class Good {
    private static ArrayList<Good> goods = new ArrayList<>();

    private String name;
    private Double buy;
    private Double sell;

    private Double number;


    public Good(String name, Double buy, Double sell) {
        this.name = name;
        this.buy = buy;
        this.sell = sell;
        this.number = 0.0;
        goods.add(this);
    }

    public Double getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Double getBuy() {
        return buy;
    }

    public Double getSell() {
        return sell;
    }

    public String goodToText() {
        return (name + "; buy: " + buy + " Sell: " + sell + " number: " + number + ".\n");
    }

    public Good getGoodByName(String name) {
        if (goods.size() == 0) {
            return null;
        } else {
            for (Good good : goods) {
                if (good.getName().equals(name)) {
                    return good;
                }
            }
        }
        return null;
    }

}
