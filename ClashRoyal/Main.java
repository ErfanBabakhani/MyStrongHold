
import java.util.Scanner;

import view.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RegisterMenu registerMenu = new RegisterMenu();
        loginMenu: while (true) {
            // System.out.println("LOGIN MENU"); // .././././.
            registerMenu.run(scanner);
            if (registerMenu.mainMenuStatus) {
                MainMenu mainMenu = new MainMenu(registerMenu.loginedUser);
                mainMenu: while (true) {
                    mainMenu.run(scanner);
                    if (mainMenu.logoutStatus) {
                        continue loginMenu;
                    } else if (mainMenu.gameMenu) { // gameMenu

                        GameMenu gameMenu = new GameMenu(mainMenu.currentUser, mainMenu.guestPlayer, mainMenu.turn);
                        while (true) {
                            // System.out.println("GAME MENU"); // ././././.
                            // int a[]=new int[1]; ///././././
                            // System.out.println(a[-1]);  ///./././/.
                            gameMenu.run(scanner);
                            if (gameMenu.logoutStatus) {
                                continue loginMenu;
                            } else if (gameMenu.mainMenu) {                                
                                continue mainMenu;
                            }
                        }
                    } else if (mainMenu.profileMenu) { // profileMenu
                        ProfileMenu profileMenu = new ProfileMenu(mainMenu.currentUser);
                        while (true) {
                            // System.out.println("PROFILE MENU"); // ./././.
                            profileMenu.run(scanner);
                            if (profileMenu.logoutStatus) {
                                continue loginMenu;
                            } else if (profileMenu.mainMenu) {
                                continue mainMenu;
                            }
                        }
                    } else if (mainMenu.shopMenu) { // shopMenu
                        ShopMenu shopMenu = new ShopMenu(mainMenu.currentUser);
                        while (true) {
                            // System.out.println("SHOP MENU"); // ./././.
                            shopMenu.run(scanner);
                            if (shopMenu.logoutStatus) {
                                continue loginMenu;
                            } else if (shopMenu.mainMenu) {
                                continue mainMenu;
                            }
                        }
                    }
                }
            }
        }
    }
}