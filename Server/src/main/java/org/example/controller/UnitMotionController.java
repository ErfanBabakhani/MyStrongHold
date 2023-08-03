package org.example.controller;

import org.example.model.GameInfo.*;

import java.util.ArrayList;

public class UnitMotionController {
    private static Integer counter = 0;
    private ArrayList<PatrolTroops> patrolTroops = new ArrayList<>();
    private Map map;
    private MapController mapController;

    public UnitMotionController(Map map, Game game) {
        this.map = map;
        mapController = new MapController(map, game);
    }

    public ArrayList<Home> findTheDestinationRace(int speed, int firstX, int firstY, int targetX, int targetY) {
        ArrayList<Home> homes = new ArrayList<>();
        boolean[] end = new boolean[1];
        end[0] = false;
        speed = reduceSpeedIfInWater(speed, mapController.getHomeByPosition(firstX, firstY));
        return findRace(speed, homes, firstX, firstY, targetX, targetY);
    }
    private ArrayList<Home> findRace(int speed, ArrayList<Home> raceHomes, int currentX, int currentY, int targetX, int targetY) {
        if (currentX - targetX > speed || currentX - targetX < -speed || currentY - targetY > speed || currentY - targetY < -speed) {
            return raceHomes;
        }
        if (currentX > targetX) {
            for (int i = currentX; i >= targetX; i--) {
                raceHomes.add(mapController.getHomeByPosition(i, currentY));
            }
        } else {
            for (int i = currentX; i <= targetX; i++) {
                raceHomes.add(mapController.getHomeByPosition(i, currentY));
            }
        }
        Integer lastX = raceHomes.get(raceHomes.size() - 1).getX();
        if (currentY > targetY) {
            for (int i = currentY - 1; i >= targetY; i--) {
                raceHomes.add(mapController.getHomeByPosition(lastX, i));
            }
        } else {
            for (int i = currentY + 1; i <= targetY; i++) {
                raceHomes.add(mapController.getHomeByPosition(lastX, i));
            }
        }
        System.out.println("<<findRace>> " + raceHomes.size());
        return raceHomes;
    }

    public boolean isAnyAvailableDestination(int speed, int firstX, int firstY, int targetX, int targetY) {
        ArrayList<Home> homes = new ArrayList<>();
        speed = reduceSpeedIfInWater(speed, mapController.getHomeByPosition(firstX, firstY));
        ArrayList<Home> race = (findRace(speed, homes, firstX, firstY, targetX, targetY));
        System.out.println("<<isAnyAvailabe..>> " + race.size());
        if (race.size() > 0)
            return true;
        return false;
    }

    public ArrayList<PatrolTroops> getPatrolTroops() {
        return patrolTroops;
    }

    public void addPatrolTroops(PatrolTroops patrolTroops) {
        this.patrolTroops.add(patrolTroops);
    }

    public void removeThePatrolTroopsByIndex(int index) {
        this.patrolTroops.remove(index);
    }

    private Integer reduceSpeedIfInWater(Integer firstSpeed, Home home) {
        if (home.getTypeOfFloor().equals("DeapLessWater"))
            return firstSpeed * 2 / 3;
        return firstSpeed;
    }
}
