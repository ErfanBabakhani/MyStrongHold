package org.example.model.GameInfo;

import java.util.HashMap;

public class StockPile extends Store {
    private final Government owner;

    public StockPile(Government owner) {
        super(100.0, owner, "Stockpile", 100, 0, 0, 0, 0);
        this.owner = owner;
        setElements("stone", 0.0);
        setElements("wood", 0.0);
        setElements("iron", 0.0);
        setElements("pitch", 0.0);
        setElements("wheat", 0.0);
        setElements("flour", 0.0);
    }

    public void setElements(String elementsKey, Double amount) {
        super.setElements(elementsKey, amount);
    }

    public HashMap<String, Double> getElements() {
        return super.getElements();
    }

    public Double getCapacity() {
        return super.getCapacity();
    }

    public void setCapacity(Double capacity) {
        super.setCapacity(capacity);
    }
}
