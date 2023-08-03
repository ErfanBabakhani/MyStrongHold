package org.example.controller;

import org.example.model.ActiveGame;
import org.example.model.DataBase;
import org.example.model.GameInfo.Game;
import org.example.model.GameInfo.Government;
import org.example.model.GameInfo.Map;
import org.example.model.User;
import org.example.view.GameMenu;
import org.example.view.LoginMenu;
import org.example.view.Menu;

import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class LoginController extends CheckController {
    public static void start(Socket socket) {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.run(socket);
    }

    public static void winner(Game game, Socket socket) {
        User user = game.getGovernments().get(0).getOwner();
        for (int i = 0; i < game.getGovernments().size(); i++) {
            if (game.getGovernments().get(i).getOwner().getHighScore() > user.getHighScore())
                user = game.getGovernments().get(i).getOwner();
        }
        LoginMenu.winnerPrint("Lord " + user.getNickname() + " wins.", socket);
    }


    public static String startGame(Matcher matcher, User owner, Socket socket) {
        if (!matcher.group("num").matches("\\d+"))
            return "Please enter a number for player number (\\d)\n";
        if (!matcher.group("turn").matches("\\d+"))
            return "Please enter a number for number of turns (\\d)\n";
        if (!matcher.group("x").matches("\\d+"))
            return "Please enter a number for xmap (\\d)\n";
        if (!matcher.group("y").matches("\\d+"))
            return "Please enter a number for ymap (\\d)\n";
        String massage = "Enter a new player username";
        int playerNumber = Integer.parseInt(matcher.group("num"));
        Map map = new Map(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), playerNumber);
        Game game = new Game(Integer.parseInt(matcher.group("turn")), map, playerNumber);
        Government government = new Government(owner);
        government.setJoined(true);
        owner.setHighScore(10);
        game.addGovernment(government);
        String username;
        DataBase.writeAMessageToClient("Enter a new player username", socket);
        while (true) {
            if (playerNumber == 1) {
                DataBase.writeAMessageToClient("Game is started", socket);
                break;
            }
            username = LoginMenu.getPlayers(massage, socket);
            if (DataBase.getUserByName(username) == null) {
                if (!username.equals(""))
                    DataBase.writeAMessageToClient("invalid person", socket);
            } else {
                Government newgovernment = new Government(DataBase.getUserByName(username));
                game.addGovernment(newgovernment);
                DataBase.getUserByName(username).setHighScore(10);
                playerNumber--;
                DataBase.writeAMessageToClient(username + " is added to game remain " + (playerNumber - 1) + " more person", socket);
            }
        }
        GameController gameController = new GameController(game, game.getGovernments().get(0));
        gameController.start(game, socket);
        return "";
    }

    public static void joinGame(Socket socket) {
        ArrayList<ActiveGame> allActiveGame = DataBase.getAllActiveGames();
        for (int i = 0; i < allActiveGame.size(); i++) {
            ArrayList<Government> allGovernment = allActiveGame.get(i).getGame().getGovernments();
            for (int j = 0; j < allGovernment.size(); j++) {
                if (allGovernment.get(j).getOwner().equals(Menu.logedInUser)) {
                    joinToGame(allGovernment.get(j), allActiveGame.get(i), socket);
                    System.out.println("We joined you to game");
                    DataBase.writeAMessageToClient("We joined you to game", socket);
                }
            }
        }
        DataBase.writeAMessageToClient("We can not find the game for you", socket);
    }

    public static void joinGame(Socket socket, int gameId) {
        if (gameId > ActiveGame.getAll().size() - 1) {
            DataBase.writeAMessageToClient("Not valid id\nid is between 0 to " + (ActiveGame.getAll().size() - 1), socket);
        }
        ArrayList<Government> allGovernment = ActiveGame.getAll().get(gameId).getGame().getGovernments();
        for (int j = 0; j < allGovernment.size(); j++) {
            if (allGovernment.get(j).getOwner().equals(Menu.logedInUser)) {
                joinToGame(allGovernment.get(j), ActiveGame.getAll().get(gameId), socket);
                DataBase.writeAMessageToClient("We joined you to game", socket);
            }
        }

        DataBase.writeAMessageToClient("We can not find the game for you", socket);
    }

    private static void joinToGame(Government government, ActiveGame activeGame, Socket socket) {
        government.setJoined(true);
        GameMenu gameMenu = new GameMenu();
        gameMenu.setGameController(activeGame.getGameController());
        gameMenu.run(government, socket);
    }

    public static String showAllGame() {
        ArrayList<ActiveGame> activeGames = ActiveGame.getAll();
        String out = "";
        for (int i = 0; i < activeGames.size(); i++) {
            out += "Game Id " + i + " : \n";
            for (int j = 0; j < activeGames.get(i).getGame().getGovernments().size(); j++) {
                out += "Government " + (j + 1) + " : " + activeGames.get(i).getGame().getGovernments().get(j).getOwner().getNickname() + "\n";
            }
            out += "-------------------\n";
        }
        return out;
    }

    public static String logout() {
        Menu.setLogedInUser(null);
        logedInuser = null;
        return "user logged out successfully!";
    }
}
