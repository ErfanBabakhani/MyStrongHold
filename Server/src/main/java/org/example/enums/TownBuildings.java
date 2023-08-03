package org.example.enums;

public enum TownBuildings {
    Inn("Inn", 0, 0),
    Hovel("Hovel", 8, 0),
    Church("Church", 0, 2),
    Cathedral("Cathedral", 0, 2),
    ;
    private String name;
    private Integer populationIncrease;
    private Integer popularityIncrease;

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
