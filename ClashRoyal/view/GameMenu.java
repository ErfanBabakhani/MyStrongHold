package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import  Fight;
import model.*;
import controller.*;
import model.Cart;
import model.DataBase;
import model.Map;
import model.Tower;
import model.Turn;

public class GameMenu extends MainView {
    DataBase dataBase = DataBase.getInstance();
    private static String menuName = "Game Menu";
    private User currentUser;
    private User guest;
    private int turnMaxNum;
    private int cuurentTurn;
    private GameController gameController = new GameController(this);

    private Map map;
    private Fight fight;
    private ArrayList turns = new ArrayList<Turn>();
    public boolean logoutStatus;
    public boolean mainMenu;
    static private Pattern[] patterns = new Pattern[10];

    static {
        patterns[0] = Pattern.compile("^show current menu$");
        patterns[1] = Pattern.compile("^show the hitpoints left of my opponent$");
        patterns[2] = Pattern.compile("^show line info (.+)$");
        patterns[3] = Pattern.compile("^number of cards to play$");
        patterns[4] = Pattern.compile("^number of moves left$");
        patterns[5] = Pattern.compile("^move troop in line (.+) and row (-{0,1}\\d+) (.+)$");
        patterns[6] = Pattern.compile("^deploy troop (.+) in line (.+) and row (-{0,1}\\d+)$");
        patterns[7] = Pattern.compile("^deploy spell Heal in line (.+) and row (.+)$");
        patterns[8] = Pattern.compile("^deploy spell Fireball in line (.+)$");
        patterns[9] = Pattern.compile("^next turn$");
    }

    public GameMenu(User host, User guest, int turnMaxNum) {
        setCurrentUser(host);
        setGuest(guest);
        setTurnNum(turnMaxNum);
        this.map = new Map(this.currentUser, this.guest);
        this.fight = new Fight(map);
        this.cuurentTurn = 1;
        this.turns.add(new Turn());
    }

    public void run(Scanner scanner) {
        String userInpu = scanner.nextLine();
        String commandResult = gameController.gameRunner(patterns, userInpu);
        System.out.print(commandResult);

    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public void setTurnNum(int turnMaxNum) {
        this.turnMaxNum = turnMaxNum;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Fight getFight() {
        return fight;
    }

    public User getGuest() {
        return guest;
    }

    public Map getMap() {
        return map;
    }

    public static String getMenuName() {
        return menuName;
    }

    public ArrayList getTurns() {
        return turns;
    }

    public int getTurnMaxNum() {
        return turnMaxNum;
    }

    public int getCuurentTurn() {
        return cuurentTurn;
    }

    public void increaseCurrentTurn() {
        this.cuurentTurn++;
    }

}
