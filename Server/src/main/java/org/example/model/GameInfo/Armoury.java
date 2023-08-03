package org.example.model.GameInfo;

public class Armoury extends Store {
    private Government owner;

    public Armoury(Government owner) {
        super(100.0, owner, "Armoury", 100, 0, 0, 0, 5);
        this.owner = owner;
        setElements("LeatherArmour", 0.0);
        setElements("Mace", 0.0);
        setElements("Sword", 0.0);
        setElements("MetalArmour", 0.0);
        setElements("Bow", 0.0);
        setElements("Crossbow", 0.0);
        setElements("Spear", 0.0);
        setElements("Pike", 0.0);
        setElements("Horse", 0.0);
    }

    public void setElements(String elementsKey, Double amount) {
        super.setElements(elementsKey, amount);
    }

    public Double getElements(String weaponName) {
        return super.getElements(weaponName);
    }

    public Double getCapacity() {
        return super.getCapacity();
    }

    public void setCapacity(Double capacity) {
        super.setCapacity(capacity);
    }
}
