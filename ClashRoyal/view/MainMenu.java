package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.*;
import controller.*;

public class MainMenu extends MainView {
    DataBase dataBase = DataBase.getInstance();
    private static String menuName = "Main Menu";
    public User currentUser;
    public boolean logoutStatus;
    public boolean profileMenu;
    public boolean shopMenu;
    public boolean gameMenu;
    public User guestPlayer;
    public int turn;
    static Pattern[] patterns = new Pattern[7];
    static Pattern[] subPatterns = new Pattern[1];

    static {
        patterns[0] = Pattern.compile("^show current menu$");
        patterns[1] = Pattern.compile("^list of users$");
        patterns[2] = Pattern.compile("^scoreboard$");
        patterns[3] = Pattern.compile("^logout$");
        patterns[4] = Pattern.compile("^profile menu$");
        patterns[5] = Pattern.compile("^shop menu$");
        patterns[6] = Pattern.compile("^start game turns count ([-]{0,1}[\\d]+) username (.+)$");
        // for start game command
        subPatterns[0] = Pattern.compile("^start game turns count ([-]{0,1}[\\d])+ username ([A-Za-z]+)$");
    }

    public MainMenu(User user) {
        setCurrentUser(user);
    }

    public void run(Scanner scanner) {
        this.logoutStatus = false;
        this.gameMenu = false;
        this.shopMenu = false;
        this.profileMenu = false;
        String userInput = scanner.nextLine();
        boolean checkCommandsTruth = false;
        Matcher matcher = patterns[0].matcher(userInput);
        int i;
        for (i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(userInput);
            if (matcher.find()) {
                checkCommandsTruth = true;
                break;
            }
        }
        if (!checkCommandsTruth) {
            System.out.println("Invalid command!");
            return;
        } else {
            if (i == 0) {
                System.out.println(menuName);
            } else if (i == 1) {
                printAllUsers();

            } else if (i == 2) {
                ArrayList sortedUsers = mainController.sortRankByLevelAndExperience();
                mainController.sortUsersByName(sortedUsers);
                printScoreboard(sortedUsers);
            } else if (i == 3) {
                this.logoutStatus = true;
                System.out.println("User " + currentUser.getUsername() + " logged out successfully!");
                return;
            } else if (i == 4) {
                this.profileMenu = true;
                System.out.println("Entered profile menu!");
                return;
            } else if (i == 5) {
                this.shopMenu = true;
                System.out.println("Entered shop menu!");
                return;
            } else if (i == 6) {
                int turn = Integer.parseInt(matcher.group(1));
                if (turn < 5 || turn > 30) {
                    System.out.println("Invalid turns count!");
                    return;
                } else {
                    matcher = subPatterns[0].matcher(userInput);
                    if (!matcher.find()) {
                        System.out.println("Incorrect format for username!");
                        return;
                    } else {
                        if (mainController.getUserByUsername(matcher.group(2)).getUsername() == null) {
                            System.out.println("Username doesn't exist!");
                            return;
                        } else {
                            this.gameMenu = true;
                            setGuestPlayer(mainController.getUserByUsername(matcher.group(2)));
                            setTurn(turn);
                            System.out.println("Battle started with user " + matcher.group(2));
                            return;
                        }
                    }
                }
            }
        }
    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setGuestPlayer(User guestPlayer) {
        this.guestPlayer = guestPlayer;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private void printAllUsers() {
        ArrayList allUsers = this.dataBase.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            System.out.println("user " + (i + 1) + ": " + ((User) allUsers.get(i)).getUsername());
        }
    }

    private void printScoreboard(ArrayList targettUsers) {
        for (int i = 0; i < 5; i++) {
            if (i > targettUsers.size() - 1)
                break;
            System.out.println((i + 1) + "- username: " + ((User) targettUsers.get(i)).getUsername() + " level: "
                    + ((User) targettUsers.get(i)).getLevel() + " experience: "
                    + ((User) targettUsers.get(i)).getExperience());
        }
    }

    // public static void main(String[] args) {
    // MainMenu mainMenu = new MainMenu(null);
    // mainMenu.dataBase.addUser(new User("Ali", "Q"));
    // mainMenu.dataBase.addUser(new User("Erfan", "AAA"));
    // mainMenu.dataBase.addUser(new User("Aw", "s"));
    // dataBase.getUserByUsername("Aw").setLevel(2);
    // mainMenu.printAllUsers();
    // User user=mainMenu.dataBase.getUserByUsername("Erfan");
    // ((User)mainMenu.dataBase.getUserByUsername("Erfan")).setLevel(2);
    // ArrayList sortedByLeveAndExperienceUsers =
    // mainMenu.sortRankByLevelAndExperience();
    // mainMenu.sortUsersByName(sortedByLeveAndExperienceUsers);
    // for (int i = 0; i < sortedByLeveAndExperienceUsers.size(); i++) {
    // System.out.println(((User)sortedByLeveAndExperienceUsers.get(i)).getUsername());
    // }
    // }

}
