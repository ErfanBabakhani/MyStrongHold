package org.example.model.GameInfo;

import java.util.ArrayList;

public class PatrolTroops {
    private Government government;
    private ArrayList<Troop> patrolTroops;
    private Home currentHome;
    private int racePatrollingCountUntilNow = 0;
    private ArrayList<Home> race;

    public PatrolTroops(ArrayList<Troop> patrolTroops, ArrayList<Home> race, Government government) {
        this.government = government;
        this.patrolTroops = patrolTroops;
        this.race = race;
        this.currentHome = race.get(0);
    }

    public Government getGovernment() {
        return government;
    }

    public ArrayList<Troop> getPatrolTroops() {
        return patrolTroops;
    }

    public Home getCurrentHome() {
        return currentHome;
    }

    public int getRacePatrollingCountUntilNow() {
        return racePatrollingCountUntilNow;
    }

    public ArrayList<Home> getRace() {
        return race;
    }

    public void increaseRaceCount() {
        this.racePatrollingCountUntilNow++;
    }


}
