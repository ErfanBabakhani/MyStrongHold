package org.example.enums;

public enum TownBuildings {
    Inn("Inn", 0, 0),
    Hovel("Hovel", 8, 0),
    Church("Church", 0, 2),
    Cathedral("Cathedral", 0, 2),
    ;
    private final String name;
    private final Integer populationIncrease;
    private final Integer popularityIncrease;

    TownBuildings(String name, Integer populationIncrease, Integer popularityIncrease) {
        this.name = name;
        this.populationIncrease = populationIncrease;
        this.popularityIncrease = popularityIncrease;
    }

    public String getName() {
        return name;
    }

    public Integer getPopulationIncrease() {
        return populationIncrease;
    }

    public Integer getPopularityIncrease() {
        return popularityIncrease;
    }
}
