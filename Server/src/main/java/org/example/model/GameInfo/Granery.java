package org.example.model.GameInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Granery extends Store {
    private Government owner;
    public static final ArrayList<String> foodNames = new ArrayList<>();

    static {
        foodNames.add("Apple");
        foodNames.add("Meat");
        foodNames.add("Cheese");
        foodNames.add("Bread");
    }

    public Granery(Government owner) {
        super(100.0, owner, "Granery", 350, 0, 100, 0, 0);
        this.owner = owner;
        setAllFoods(foodNames.get(0), 0.0);
        setAllFoods(foodNames.get(1), 0.0);
        setAllFoods(foodNames.get(2), 0.0);
        setAllFoods(foodNames.get(3), 0.0);
    }

    public void setAllFoods(String elementsKey, Double amount) {
        super.setElements(elementsKey, amount);
    }

    public HashMap<String, Double> getAllFoods() {
        return super.getElements();
    }

    public Double getCapacity() {
        return super.getCapacity();
    }

    public void setCapacity(Double capacity) {
        super.setCapacity(capacity);
    }

}
