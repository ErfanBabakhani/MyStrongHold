package org.example.model.GameInfo;

public class Good {
    public Good(String goodName, Integer price) {
        this.goodName = goodName;
        this.price = price;
    }

    private final String goodName;
    private final Integer price;
    private int count = 0;

    public String getGoodName() {
        return goodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getPrice() {
        return price;
    }
}
