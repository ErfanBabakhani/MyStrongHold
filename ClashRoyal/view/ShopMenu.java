package view;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Cart;
import model.DataBase;
import model.User;

public class ShopMenu extends MainView{
    private static String menuName = "Shop Menu";
    private User currentUser;
    public boolean logoutStatus;
    public boolean mainMenu;
    private DataBase dataBase = DataBase.getInstance();
    static private Pattern[] patterns = new Pattern[4];
    static {
        patterns[0] = Pattern.compile("^show current menu$");
        patterns[1] = Pattern.compile("^back$");
        patterns[2] = Pattern.compile("^buy card (.+)$");
        patterns[3] = Pattern.compile("^sell card (.+)$");
        // for
    }

    public ShopMenu(User user) {
        setCurrentUser(user);
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        this.logoutStatus = false;
        this.mainMenu = false;
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
                return;
            } else if (i == 1) {
                mainMenu = true;
                System.out.println("Entered main menu!");
                return;
            } else if (i == 2) {
                if (!mainController.varyficationCartName(matcher.group(1))) {
                    System.out.println("Invalid card name!");
                    return;
                } else if (!haveYouEnughMonyToBuyCart(matcher.group(1))) {
                    System.out.println("Not enough gold to buy " + matcher.group(1) + "!");
                    return;
                } else if (currentUser.getBattleDeck().getCartByName(matcher.group(1)) != null) {
                    System.out.println("You have this card!");
                    return;
                } else {

                    currentUser.addCartToBuyedCart(new Cart(matcher.group(1), this.currentUser));
                    currentUser
                            .setGold(currentUser.getGold() - new Cart(matcher.group(1), this.currentUser).getPrice());
                    System.out.println("Card " + matcher.group(1) + " bought successfully!");
                    return;
                }

            } else if (i == 3) { // fix it
                if (!mainController.varyficationCartName(matcher.group(1))) {
                    System.out.println("Invalid card name!");
                    return;
                } else if (currentUser.getBuyedCartByName(matcher.group(1)) == null) {
                    System.out.println("You don't have this card!");
                    return;
                } else if (currentUser.getBattleDeck().getCartByName(matcher.group(1)) != null) {
                    System.out.println("You cannot sell a card from your battle deck!");
                    return;
                } else {
                    int benefit = ((int) Math.floor(0.8 * new Cart(matcher.group(1), this.currentUser).getPrice()));
                    currentUser.setGold(currentUser.getGold() + benefit);
                    currentUser.getBattleDeck()
                            .removeCartByname(new Cart(matcher.group(1), this.currentUser).getCartName());
                    currentUser.removeFromBuyedCart(
                            currentUser.getBuyedCartByName(new Cart(matcher.group(1), this.currentUser).getCartName()));
                    System.out.println("Card " + new Cart(matcher.group(1), this.currentUser).getCartName()
                            + " sold successfully!");
                }
            }
        }

    }

    private void setCurrentUser(User user) {
        this.currentUser = user;

    }

    private boolean haveYouEnughMonyToBuyCart(String cartName) {
        Cart cart = new Cart(cartName, this.currentUser);
        if (cart.getPrice() <= currentUser.getGold()) {
            return true;
        }
        return false;
    }

}
