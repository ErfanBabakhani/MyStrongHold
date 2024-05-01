package view;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.*;
import view.*;

public class MainMenu {
    public static String menuName = "main menu";
    public boolean logoutStatus;
    public boolean customerMenu;
    public boolean restaurantAdminMenu;
    public boolean SnappfoodAdminMenu;
    private User cuurentUser;
    private static Pattern[] patterns = new Pattern[3];
    private static Pattern[] subPatterns = new Pattern[3];
    static {
        patterns[0] = Pattern.compile("^\\s*enter \\s*(.+)\\s*$");
        patterns[1] = Pattern.compile("^\\s*logout\\s*$");
        patterns[2]=Pattern.compile("^\\s*show \\s*current \\s*menu\\s*$");
        subPatterns[0] = Pattern.compile("^\\s*enter \\s*customer menu\\s*$");
        subPatterns[1] = Pattern.compile("^\\s*enter \\s*restaurant admin menu\\s*$");
        subPatterns[2] = Pattern.compile("^\\s*enter \\s*Snappfood admin menu\\s*$");
    }

    public MainMenu(User currenUser) {
        this.cuurentUser = currenUser;
    }

    public void run(Scanner scanner) {
      
        String userInput = scanner.nextLine();
        userInput=userInput.replaceAll("\t", " ");
        boolean checkCommandsTruth = false;
        customerMenu = false;
        restaurantAdminMenu = false;
        SnappfoodAdminMenu = false;
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
        } else {
            if (i == 0) {

                matcher = subPatterns[0].matcher(userInput);
                customerMenu = matcher.find();
                matcher = subPatterns[1].matcher(userInput);
                restaurantAdminMenu = matcher.find();
                matcher = subPatterns[2].matcher(userInput);
                SnappfoodAdminMenu = matcher.find();
                if (customerMenu) {
                    if (!(cuurentUser instanceof Customer)) {
                        customerMenu=false;
                        System.out.println("enter menu failed: access denied");
                        return;
                    } else {
                        customerMenu=true;
                        System.out.println("enter menu successful: You are in the customer menu!");
                        return;
                    }

                } else if (restaurantAdminMenu) {
                    if (!(cuurentUser instanceof RestaurantAdmin)) {
                        restaurantAdminMenu=false;
                        System.out.println("enter menu failed: access denied");
                        return;
                    } else {
                        restaurantAdminMenu=true;
                        System.out.println("enter menu successful: You are in the restaurant admin menu!");
                        return;
                    }

                } else if (SnappfoodAdminMenu) {
                    if (!(cuurentUser instanceof SnapfoodAdmin)) {
                        SnappfoodAdminMenu=false;
                        System.out.println("enter menu failed: access denied");
                        return;
                    } else {
                        SnappfoodAdminMenu=true;
                        System.out.println("enter menu successful: You are in the Snappfood admin menu!");
                        return;
                    }
                } else {
                    System.out.println("enter menu failed: invalid menu name");
                    return;
                }

            } else if (i == 1) {
                logoutStatus=true;
                return;
            }else if(i==2){
                System.out.println(menuName);
                return;
            }
        }

    }
    // public static void main(String[] args) {
    //     User user=new User();
    //     // MainMenu mainMenu=new MainMenu(user);
    // }
}
