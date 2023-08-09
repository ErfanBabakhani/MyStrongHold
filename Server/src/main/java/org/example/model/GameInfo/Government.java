package org.example.model.GameInfo;

import org.example.model.User;

import java.util.ArrayList;

public class Government {
    private Integer unemployed = 10;
    private User owner;
    private Integer popularity = 100;
    private Integer foodRate = -2;
    private double coin = 100000;
    private Integer taxRate = 0;
    private ArrayList<Troop> troops = new ArrayList<>();
    private Integer fearRate = 0;
    private boolean isJoined = false;
    private ArrayList<Store> allStore = new ArrayList<>();

    public Government(User owner) {
        this.owner = owner;
    }

    private Integer population = 10;

    public Integer getPopularity() {
        return popularity;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public Integer getFoodRate() {
        return foodRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }


    public Integer getTaxRate() {
        return taxRate;
    }

    public Integer getFearRate() {
        return fearRate;
    }

    public double getCoin() {
        return coin;
    }

    public void setFoodRate(Integer rate) {
        this.foodRate = rate;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public void addTroop(Troop troop) {
        this.troops.add(troop);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getUnemployed() {
        return unemployed;
    }

    public void setUnemployed(Integer unemployed) {
        this.unemployed = unemployed;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }

    public ArrayList<Store> getAllStore() {
        return allStore;
    }
}