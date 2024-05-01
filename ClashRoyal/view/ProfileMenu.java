package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.*;
import controller.*;

import model.Cart;
import model.DataBase;

public class ProfileMenu extends MainView {
    DataBase dataBase = DataBase.getInstance();
    private static String menuName = "Profile Menu";
    private User currentUser;
    public boolean logoutStatus;
    public boolean mainMenu;
    private static Pattern[] patterns = new Pattern[7];
    private static Pattern[] subPatterns = new Pattern[1];
    static {
        patterns[0] = Pattern.compile("^show current menu$");
        patterns[1] = Pattern.compile("^back$");
        patterns[2] = Pattern.compile("^change password old password (.+) new password (.+)$");
        patterns[3] = Pattern.compile("^Info$");
        patterns[4] = Pattern.compile("^remove from battle deck (.+)$");
        patterns[5] = Pattern.compile("^add to battle deck (.+)$");
        patterns[6] = Pattern.compile("^show battle deck$");
        // for change password command
        // ((?=.*{8,20})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*])[^\\s\\d][^\\s]*)
        subPatterns[0] = Pattern.compile(
                "^change password old password (.+) new password ((?=[^\\s]{8,20}$)(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\!\\@\\#\\$\\%\\^\\&\\*])[^\\s\\d][^\\s]*)$");
    }

    public ProfileMenu(User user) {
        setCurrentUser(user);
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        this.logoutStatus = false;
        this.mainMenu = false;
        boolean checkCommandsTruth = false;
        int i;
        Matcher matcher = patterns[0].matcher(userInput);
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
                return;
            } else if (i == 1) {
                this.mainMenu = true;
                System.out.println("Entered main menu!");
                return;
            } else if (i == 2) {
                if (!currentUser.getPassword().equals(matcher.group(1))) {
                    System.out.println("Incorrect password!");
                    return;
                }
                matcher = subPatterns[0].matcher(userInput);
                if (!matcher.find()) {
                    System.out.println("Incorrect format for new password!");
                    return;
                } else {
                    changePassword(currentUser, matcher.group(2));
                    System.out.println("Password changed successfully!");
                    return;
                }
            } else if (i == 3) {
                info();
            } else if (i == 4) {
                int size = currentUser.getBattleDeck().getAllCarts().size();
                if (!mainController.varyficationCartName(matcher.group(1))) {
                    System.out.println("Invalid card name!");
                    return;
                } else if (currentUser.getBattleDeck().getCartByName(matcher.group(1)) == null) {
                    System.out.println("This card isn't in your battle deck!");
                    return;
                } else if (size == 1) {
                    System.out.println("Invalid action: your battle deck will be empty!");
                    return;
                }
                currentUser.getBattleDeck().removeCartByname(matcher.group(1));
                System.out.println("Card " + matcher.group(1) + " removed successfully!");
                return;
            } else if (i == 5) {
                int size = currentUser.getBattleDeck().getAllCarts().size();
                if (!mainController.varyficationCartName(matcher.group(1))) {
                    System.out.println("Invalid card name!");
                    return;
                } else if (currentUser.getBuyedCartByName(matcher.group(1)) == null) {
                    System.out.println("You don't have this card!");
                    return;
                } else if (currentUser.getBattleDeck().getCartByName(matcher.group(1)) != null) {
                    System.out.println("This card is already in your battle deck!");
                    return;
                } else if (size == 4) {
                    System.out.println("Invalid action: your battle deck is full!");
                    return;
                }
                addCartToBattleDeck(matcher.group(1), this.currentUser);
                System.out.println("Card " + matcher.group(1) + " added successfully!");
                return;
            } else if (i == 6) {
                ArrayList allCarts = currentUser.getBattleDeck().getAllCarts();
                mainController.sortCartNameLexicographic(allCarts);
                printAllCarts(allCarts);
            }
        }

    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
    }

    private void info() {
        ArrayList sortedUsers = mainController.sortRankByLevelAndExperience();
        mainController.sortUsersByName(sortedUsers);
        printInfo(sortedUsers);

    }

    private void printInfo(ArrayList sortedUsers) {
        int i;
        for (i = 0; i < sortedUsers.size(); i++) {
            if (((User) sortedUsers.get(i)).getUsername().equals(currentUser.getUsername())) {
                break;
            }
        }
        System.out.println("username: " + currentUser.getUsername());
        System.out.println("password: " + currentUser.getPassword());
        System.out.println("level: " + currentUser.getLevel());
        System.out.println("experience: " + currentUser.getExperience());
        System.out.println("gold: " + currentUser.getGold());
        System.out.println("rank: " + (i + 1));
    }

    private void addCartToBattleDeck(String name, User owner) {

        Cart cart = new Cart(name, owner);

        this.currentUser.addCartToBattleDeck(cart);
    }

    private void printAllCarts(ArrayList allCarts) {
        for (int i = 0; i < allCarts.size(); i++) {
            System.out.println(((Cart) allCarts.get(i)).getCartName());
        }
    }

}
