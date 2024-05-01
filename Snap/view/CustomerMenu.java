package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.CustomerMenuController;
import model.Category;
import model.Customer;
import model.DataBase;
import model.DiscountCode;
import model.Food;
import model.Restaurunt;

public class CustomerMenu {
    static public String menuName = "customer menu";
    public boolean logoutStatus;
    private DataBase dataBase = DataBase.getInstance();
    CustomerMenuController customerMenuController=new CustomerMenuController(this);
    private Customer cuurentCustomer;
    static Pattern[] patterns = new Pattern[11];
    static Pattern[] subPatterns = new Pattern[5];

    static {
        patterns[0] = Pattern.compile("^\\s*show \\s*restaurant( \\s*-t \\s*([^\\s]+)){0,1}\\s*$"); // group 2 is type
        patterns[1] = Pattern.compile("^\\s*show \\s*menu \\s*([^\\s]+)( \\s*-c \\s*([^\\s]+)){0,1}\\s*$"); // group1 is
        patterns[2] = Pattern
                .compile("^\\s*add \\s*to \\s*cart \\s*([^\\s]+) \\s*([^\\s]+)( \\s*-n \\s*([^\\s]+)){0,1}\\s*$"); // group1
        patterns[3] = Pattern
                .compile("^\\s*remove \\s*from \\s*cart \\s*([^\\s]+) \\s*([^\\s]+)( \\s*-n \\s*([^\\s]+)){0,1}\\s*$"); // group1
        patterns[4] = Pattern.compile("^\\s*show \\s*cart\\s*$");
        patterns[5] = Pattern.compile("^\\s*show \\s*discounts\\s*$");
        patterns[6] = Pattern.compile("^\\s*purchase \\s*cart( \\s*-d \\s*([^\\s]+)){0,1}\\s*$"); // group 2 is discount
        patterns[7] = Pattern.compile("^\\s*logout\\s*$");
        patterns[8] = Pattern.compile("^\\s*show \\s*current \\s*menu\\s*$");
        patterns[9] = Pattern.compile("^\\s*charge \\s*account \\s*([^\\s]+)\\s*$");
        patterns[10] = Pattern.compile("^\\s*show \\s*balance\\s*$");

        subPatterns[0] = Pattern.compile("^\\s*show \\s*restaurant( \\s*-t \\s*([^\\s]+))\\s*$");
        subPatterns[1] = Pattern.compile("^\\s*show \\s*menu \\s*([^\\s]+)( \\s*-c \\s*([^\\s]+))\\s*$"); // group1 is
        subPatterns[2] = Pattern
                .compile("^\\s*add \\s*to \\s*cart \\s*([^\\s]+) \\s*([^\\s]+)( \\s*-n \\s*([^\\s]+))\\s*$"); // group1
        subPatterns[3] = Pattern
                .compile("^\\s*remove \\s*from \\s*cart \\s*([^\\s]+) \\s*([^\\s]+)( \\s*-n \\s*([^\\s]+))\\s*$"); // group1
        subPatterns[4] = Pattern.compile("^\\s*purchase \\s*cart( \\s*-d \\s*([^\\s]+))\\s*$"); // group 2 is discount
    }

    public CustomerMenu(Customer cuurentCustomer) {
        this.cuurentCustomer = cuurentCustomer;
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        System.out.print(customerMenuController.customerMenuRunner(patterns, subPatterns, userInput));

    }

    public Customer getCuurentCustomer() {
        return cuurentCustomer;
    }
    public static String getMenuName() {
        return menuName;
    }
    


}
