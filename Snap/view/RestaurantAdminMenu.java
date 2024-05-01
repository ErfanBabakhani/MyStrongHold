package view;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Food;
import model.RestaurantAdmin;
import model.Restaurunt;

public class RestaurantAdminMenu {
    public static String menuName = "restaurant admin menu";
    public boolean logoutStatus;
    private RestaurantAdmin cuurentRestaurantAdmin;
    private Restaurunt cuurentRestaurunt;
    static Pattern[] patterns = new Pattern[6];
    static Pattern[] subPatterns = new Pattern[1];
    static {
        patterns[0] = Pattern.compile("^\\s*charge \\s*account \\s*([^\\s]+)\\s*$");
        patterns[1] = Pattern.compile("^\\s*show \\s*balance\\s*$");
        patterns[2] = Pattern.compile("^\\s*add \\s*food \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[3] = Pattern.compile("^\\s*remove \\s*food \\s*([^\\s]+)\\s*$");
        patterns[4] = Pattern.compile("^\\s*logout\\s*$");
        patterns[5] = Pattern.compile("^\\s*show \\s*current \\s*menu\\s*$");
        subPatterns[0] = Pattern
                .compile("\\s*add \\s*food \\s*([a-z-]+) \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+)\\s*");
    }

    public RestaurantAdminMenu(RestaurantAdmin cuurentRestaurantAdmin, Restaurunt restaurunt) {
        this.cuurentRestaurantAdmin = cuurentRestaurantAdmin;
        this.cuurentRestaurunt = restaurunt;
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        userInput=userInput.replaceAll("\t", " ");
        boolean checkCommandsTruth = false;
        logoutStatus = false;
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
            System.out.println("invalid command!");
            return;
        } else {
            if (i == 0) {
                int amount = Integer.parseInt(matcher.group(1));
                if (amount <= 0) {
                    System.out.println("charge account failed: invalid cost or price");
                    return;
                } else {
                    cuurentRestaurunt.chargeBalance(amount);
                    System.out.println("charge account successful");
                    return;
                }
            } else if (i == 1) {
                System.out.println(cuurentRestaurunt.getBalance());
                return;
            } else if (i == 2) {
                if (!matcher.group(2).equals("starter") && !matcher.group(2).equals("entree")
                        && !matcher.group(2).equals("dessert")) {
                    System.out.println("add food failed: invalid category");
                    return;
                }
                matcher = subPatterns[0].matcher(userInput);
                if (!matcher.find()) {
                    System.out.println("add food failed: invalid food name");
                    return;
                } else {
                    int price = Integer.parseInt(matcher.group(3));
                    int cost = Integer.parseInt(matcher.group(4));
                    if (cuurentRestaurunt.getFoodByName(matcher.group(1)).getName() != null) {
                        System.out.println("add food failed: food already exists");
                        return;
                    } else if (cost <= 0 || price <= 0) {
                        System.out.println("add food failed: invalid cost or price");
                        return;
                    } else {
                        Food food = new Food(matcher.group(1), price, cost);
                        cuurentRestaurunt.addFood(food, matcher.group(2));
                        System.out.println("add food successful");
                        return;
                    }
                }
            } else if (i == 3) {
                if (cuurentRestaurunt.getFoodByName(matcher.group(1)).getName() == null) {
                    System.out.println("remove food failed: food not found");
                    return;
                } else {
                    cuurentRestaurunt.removeFoodByName(matcher.group(1));
                }
            } else if (i == 4) {
                logoutStatus = true;
                return;
            } else if (i == 5) {
                System.out.println(menuName);
                return;
            }
        }
    }

}
