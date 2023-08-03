package org.example.model;

import org.example.controller.GameController;
import org.example.model.GameInfo.Game;

import java.util.ArrayList;

public class ActiveGame {
    private static int counter = 0;
    private int id;
    private Game game;
    private GameController gameController;
    private static ArrayList<ActiveGame> all = new ArrayList<>();

    public ActiveGame(Game game, GameController gameController) {
        this.game = game;
        this.gameController = gameController;
        this.id = counter;
        all.add(this);
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public GameController getGameController() {
        return gameController;
    }

    public static ArrayList<ActiveGame> getAll() {
        return all;
    }
}
