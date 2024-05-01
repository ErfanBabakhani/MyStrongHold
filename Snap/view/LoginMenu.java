package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.LoginController;
import model.*;
import view.*;
import model.Customer;
import model.DataBase;

public class LoginMenu {
    private static DataBase dataBase = DataBase.getInstance();
    public static User loginedUser;
    private static String menuName = "login menu";
    public static boolean mainMenuStatus;
    LoginController loginController = new LoginController(this);
    static Pattern[] patterns = new Pattern[6];
    static Pattern[] subPatterns = new Pattern[6];
    static {
        patterns[0] = Pattern.compile("^\\s*show \\s*current \\s*menu\\s*$");
        patterns[1] = Pattern.compile("^\\s*register \\s*[^\\s]+ \\s*[^\\s]+\\s*$");
        patterns[2] = Pattern.compile("^\\s*login \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[3] = Pattern.compile("^\\s*change \\s*password \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[4] = Pattern.compile("^\\s*remove \\s*account \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[5] = Pattern.compile("^\\s*exit\\s*$");
        subPatterns[0] = Pattern.compile("^\\s*register \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) ");
        subPatterns[1] = Pattern
                .compile("^\\s*register \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) \\s*([A-Za-z0-9_]+)\\s*$");
        subPatterns[2] = Pattern.compile(
                "^\\s*register \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) \\s*((?=[^\\s]{5,})(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])[A-Za-z0-9_]+)\\s*$");
        subPatterns[3] = Pattern.compile(
                "^\\s*change \\s*password \\s*([^\\s]+) \\s*([^\\s]+) ");
        subPatterns[4] = Pattern.compile(
                "^\\s*change \\s*password \\s*([^\\s]+) \\s*([^\\s]+) \\s*([A-Za-z0-9_]+)\\s*$");
        subPatterns[5] = Pattern.compile(
                "^\\s*change \\s*password \\s*([^\\s]+) \\s*([^\\s]+) \\s*((?=[^\\s]{5,})(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])[A-Za-z0-9_]+)\\s*$");
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        System.out.print(loginController.loginMenuRunner(patterns, subPatterns, userInput));
    }

    public static String getMenuName() {
        return menuName;
    }

}
