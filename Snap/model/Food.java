package model;
public class Food {
    private String name;
    private int price;
    private int cost;

    public Food(String name,int price,int cost) {
        setName(name);
        setPrice(price);
        setCost(cost);
    }

    private void setCost(int cost) {
        this.cost = cost;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
