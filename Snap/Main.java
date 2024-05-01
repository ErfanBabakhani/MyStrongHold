import java.util.Scanner;

import model.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String snapfoodAdminUsername;
        String snapfoodAdminPassword;
        snapfoodAdminUsername = scanner.nextLine();
        snapfoodAdminPassword = scanner.nextLine();
        SnapfoodAdmin snapfoodAdmin = SnapfoodAdmin.getInstance();
        snapfoodAdmin.setPassword(snapfoodAdminPassword);
        snapfoodAdmin.setUsername(snapfoodAdminUsername);
        DataBase dataBase = DataBase.getInstance();
        dataBase.addSnapfoodAdmin(snapfoodAdmin);
        LoginMenu loginMenu = new LoginMenu();
        First: while (true) {

            loginMenu.run(scanner);
            if (loginMenu.mainMenuStatus) {
                MainMenu mainMenu = new MainMenu(loginMenu.loginedUser);
                while (true) {

                    mainMenu.run(scanner);
                    if (mainMenu.logoutStatus) {
                        break;
                    } else if (mainMenu.customerMenu) {
                        CustomerMenu customerMenu = new CustomerMenu(((Customer) loginMenu.loginedUser));
                        while (true) {
                            customerMenu.run(scanner);
                            if (customerMenu.logoutStatus) {
                                continue First;
                            }
                        }

                    } else if (mainMenu.restaurantAdminMenu) {
                        String UserName = (loginMenu.loginedUser).getUsername();
                        Restaurunt restaurunt = dataBase.getRestauruntByUsername(UserName);
                        if (restaurunt.getUsername() == null) {
                            System.out.println("OOOOOPPS");
                        }
                        RestaurantAdminMenu restaurantAdminMenu = new RestaurantAdminMenu(
                                ((RestaurantAdmin) loginMenu.loginedUser), restaurunt);
                        while (true) {
                            restaurantAdminMenu.run(scanner);
                            if (restaurantAdminMenu.logoutStatus) {
                                continue First;
                            }
                        }

                    } else if (mainMenu.SnappfoodAdminMenu) {
                        SnapfoodAdminMenu snapfoodAdminMenu = new SnapfoodAdminMenu(
                                ((SnapfoodAdmin) loginMenu.loginedUser));
                        while (true) {
                            snapfoodAdminMenu.run(scanner);
                            if (snapfoodAdminMenu.logoutStatus) {
                                continue First;
                            }
                        }

                    }

                }
            }
        }

    }

}
