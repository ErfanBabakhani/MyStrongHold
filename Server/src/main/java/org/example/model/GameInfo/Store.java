package org.example.model.GameInfo;

import java.util.HashMap;

public class Store extends Building {
    private Double maxCapacity = 0.0;

    protected Double space = maxCapacity;

    HashMap<String, Double> elements = new HashMap<>();

    public Store(Double maxCapacity, Government owner, String type, Integer maxHitpoint, Integer needWorkers, Integer price, Integer neededStone, Integer neededWood) {
        super(owner, type, maxHitpoint, needWorkers, price, neededStone, neededWood);
        this.maxCapacity = maxCapacity;
        this.space = maxCapacity;
    }

    public void setElements(String elementsKey, Double amount) {
        elements.put(elementsKey, amount);
    }

    public Double getElements(String good) {
        return elements.get(good);
    }

    public HashMap<String, Double> getElements() {
        return elements;
    }

    public Double getCapacity() {
        return space;
    }

    public void setCapacity(Double capacity) {
        space = capacity;
    }

    public void changeCapacity(Double capacity) {
        space -= capacity;
    }

}
