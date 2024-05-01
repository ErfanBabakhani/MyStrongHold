package model;
import java.util.ArrayList;

public class Category {
    private String categorName;
    private ArrayList foods = new ArrayList<Food>();

    public Category(String categoryName) {
        this.categorName=categoryName;
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    public ArrayList getFoods() {
        return foods;
    }
    public String getCategorName() {
        return categorName;
    }

}
