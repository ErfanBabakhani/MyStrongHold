package view;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Customer;
import model.DataBase;
import model.DiscountCode;
import model.RestaurantAdmin;
import model.Restaurunt;
import model.SnapfoodAdmin;

public class SnapfoodAdminMenu {
    public static String menuName = "Snappfood admin menu";
    private static DataBase dataBase = DataBase.getInstance();
    private SnapfoodAdmin cuurentSnapfoodAdmin;
    public boolean logoutStatus;
    static Pattern[] patterns = new Pattern[7];
    static Pattern[] subPatterns = new Pattern[6];
    static {
        patterns[0] = Pattern.compile("^\\s*add \\s*restaurant \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[1] = Pattern.compile("^\\s*show \\s*restaurant( \\s*-t \\s*([^\\s]+)){0,1}\\s*$"); 
        patterns[2] = Pattern.compile("^\\s*remove \\s*restaurant \\s*([^\\s]+)\\s*");
        patterns[3] = Pattern.compile("^\\s*set \\s*discount \\s*([^\\s]+) \\s*([^\\s]+) \\s*([^\\s]+)\\s*$");
        patterns[4] = Pattern.compile("^\\s*show \\s*discounts\\s*$");
        patterns[5] = Pattern.compile("^\\s*logout\\s*$");
        patterns[6] = Pattern.compile("^\\s*show \\s*current \\s*menu\\s*$");
        subPatterns[0] = Pattern.compile("^\\s*add \\s*restaurant \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) "); 
                                                                                                            
        subPatterns[1] = Pattern
                .compile("^\\s*add \\s*restaurant \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) \\s*([A-Za-z0-9_]+) "); 
        subPatterns[2] = Pattern.compile(
                "^\\s*add \\s*restaurant \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) \\s*((?=[^\\s]{5,})(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])[A-Za-z0-9_]+) "); 
        subPatterns[3] = Pattern.compile(
                "^\\s*add \\s*restaurant \\s*((?=[^\\s]*[A-Za-z])[A-Za-z0-9_]+) \\s*((?=[^\\s]{5,})(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])[A-Za-z0-9_]+) \\s*([a-z-]+)\\s*$");
        subPatterns[4] = Pattern.compile("^\\s*show \\s*restaurant( \\s*-t \\s*([^\\s]+))\\s*$");
        subPatterns[5] = Pattern.compile("^\\s*set \\s*discount \\s*([^\\s]+) \\s*([^\\s]+) \\s*([A-Za-z]+)\\s*$");
    }

    public SnapfoodAdminMenu(SnapfoodAdmin cuurentSnapfoodAdmin) {
        this.cuurentSnapfoodAdmin = cuurentSnapfoodAdmin;
    }

    public void run(Scanner scanner) {
        String userInput = scanner.nextLine();
        userInput = userInput.replaceAll("\t", " ");
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
            System.out.println("invalid command!");
            return;
        } else {
            if (i == 0) {
                matcher = subPatterns[0].matcher(userInput);
                if (!matcher.find()) {
                    System.out.println("add restaurant failed: invalid username format");

                    return;
                } else {
                    int a[] = new int[1];
                    if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() != null) {
                        System.out.println("add restaurant failed: username already exists");
                        return;
                    }
                    matcher = subPatterns[1].matcher(userInput);
                    if (!matcher.find()) {
                        System.out.println("add restaurant failed: invalid password format");
                        return;
                    } else {
                        matcher = subPatterns[2].matcher(userInput);
                        if (!matcher.find()) {
                            System.out.println("add restaurant failed: weak password");
                            return;
                        } else {

                            matcher = subPatterns[3].matcher(userInput);
                            if (!matcher.find()) {
                                System.out.println("add restaurant failed: invalid type format");
                                return;
                            } else {
                                addRestauratnToDataBase(matcher);
                                registerRestaurantAdmin(matcher);
                                System.out.println("add restaurant successful");
                                return;
                            }
                        }
                    }
                }
            } else if (i == 1) {
                matcher = subPatterns[4].matcher(userInput);
                if (matcher.find()) {
                    printRestaurantsList(dataBase.getRestaurantByType(matcher.group(2)));
                    return;
                } else {
                    printRestaurantsList(dataBase.getRestuarunts());
                    return;
                }
            } else if (i == 2) {
                if (dataBase.getRestauruntByUsername(matcher.group(1)).getUsername() == null) {
                    System.out.println("remove restaurant failed: restaurant not found");
                    return;
                } else {
                    removeRestaurant(matcher.group(1));
                    removeRestaurantAdmin(matcher.group(1));
                }
            } else if (i == 3) {
                int discountAmount = Integer.parseInt(matcher.group(2));
                if (!(dataBase.getUsersByUsername(matcher.group(1)) instanceof Customer)
                        || dataBase.getUsersByUsername(matcher.group(1)).getUsername() == null) {
                    System.out.println("set discount failed: username not found");
                    return;
                } else if (discountAmount <= 0) {
                    System.out.println("set discount failed: invalid amount");
                    return;
                }
                matcher = subPatterns[5].matcher(userInput);
                if (!matcher.find()) {
                    System.out.println("set discount failed: invalid code format");
                    return;
                } else {
                    Customer customer = ((Customer) dataBase.getUsersByUsername(matcher.group(1)));
                    DiscountCode discountCode = new DiscountCode(matcher.group(3), discountAmount, matcher.group(1));
                    setDiscountForCustomer(customer, discountCode);
                    System.out.println("set discount successful");
                    return;
                }
            } else if (i == 4) {
                printDiscountCdoe(this.dataBase.getDiscountCode());
            } else if (i == 5) {
                logoutStatus = true;
                return;
            } else if (i == 6) {
                System.out.println(menuName);
                return;
            }
        }
    }

    public void addRestauratnToDataBase(Matcher matcher) {
        Restaurunt restaurunt = new Restaurunt(matcher.group(1), matcher.group(3));
        this.dataBase.addRestauratn(restaurunt);
    }

    public void registerRestaurantAdmin(Matcher matcher) {
        RestaurantAdmin restaurantAdmin = new RestaurantAdmin(matcher.group(1), matcher.group(2));
        this.dataBase.addRestaurantAdmin(restaurantAdmin);
    }

    public void removeRestaurant(String username) {
        Restaurunt targetRestaurunt = this.dataBase.getRestauruntByUsername(username);
        this.dataBase.removeRestaurant(targetRestaurunt);
    }

    public void removeRestaurantAdmin(String username) {
        RestaurantAdmin targetRestaurantAdmin = ((RestaurantAdmin) this.dataBase.getUsersByUsername(username));
        this.dataBase.removeUser(targetRestaurantAdmin);

    }

    public void printRestaurantsList(ArrayList restauranList) {
        for (int i = 0; i < restauranList.size(); i++) {
            System.out.println((i + 1) + ") " + ((Restaurunt) restauranList.get(i)).getUsername() + ": type="
                    + ((Restaurunt) restauranList.get(i)).getType() + " balance="
                    + ((Restaurunt) restauranList.get(i)).getBalance());
        }
    }

    public void setDiscountForCustomer(Customer customer, DiscountCode discountCode) {
        customer.setDiscount(discountCode);
        this.dataBase.addDiscount(discountCode);
    }

    public void printDiscountCdoe(ArrayList discountCodeList) {
        for (int i = 0; i < discountCodeList.size(); i++) {
            System.out.println((i + 1) + ") " + ((DiscountCode) discountCodeList.get(i)).getName() + " | amount="
                    + ((DiscountCode) discountCodeList.get(i)).getAmount() + " --> user="
                    + ((DiscountCode) discountCodeList.get(i)).getCustomerUsername());
        }
    }

}
