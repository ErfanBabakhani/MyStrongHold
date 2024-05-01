package controller;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

import view.CustomerMenu;
import model.*;
import controller.*;

public class CustomerMenuController {
    private CustomerMenu customerMenu;
    DataBase dataBase = DataBase.getInstance();

    public CustomerMenuController(CustomerMenu customerMenu) {
        setCustomerMenu(customerMenu);
    }

    private void setCustomerMenu(CustomerMenu customerMenu) {
        this.customerMenu = customerMenu;
    }

    public String customerMenuRunner(Pattern[] patterns, Pattern[] subPatterns, String userInput) {
        customerMenu.logoutStatus = false;
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
            return ("invalid command!\n");

        } else if (i == 0) {
            matcher = subPatterns[0].matcher(userInput);
            if (matcher.find()) {
                return restaurantsList(dataBase.getRestaurantByType(matcher.group(2)));

            } else {
                return restaurantsList(dataBase.getRestuarunts());
            }
        } else if (i == 1) {
            if (dataBase.getRestauruntByUsername(matcher.group(1)).getUsername() == null) {
                return ("show menu failed: restaurant not found\n");
            } else {
                Restaurunt restaurunt = dataBase.getRestauruntByUsername(matcher.group(1));
                matcher = subPatterns[1].matcher(userInput);
                if (matcher.find()) {
                    String category = matcher.group(3);
                    if (category.equals("starter")) {
                        return speceficCategoryMenu(restaurunt, category);
                    } else if (category.equals("entree")) {
                        return speceficCategoryMenu(restaurunt, category);
                    } else if (category.equals("dessert")) {
                        return speceficCategoryMenu(restaurunt, category);
                    } else {
                        return ("show menu failed: invalid category\n");
                    }
                } else {
                    return menuAllCategory(restaurunt);

                }
            }
        } else if (i == 2) {
            Restaurunt restaurunt = dataBase.getRestauruntByUsername(matcher.group(1));
            if (restaurunt.getUsername() == null) {
                return ("add to cart failed: restaurant not found\n");

            } else if (restaurunt.getFoodByName(matcher.group(2)).getName() == null) {
                return ("add to cart failed: food not found\n");

            }
            Matcher matcher2 = subPatterns[2].matcher(userInput);
            if (matcher2.find()) {
                int amount = Integer.parseInt(matcher2.group(4));
                if (amount <= 0) {
                    return ("add to cart failed: invalid number\n");
                } else {
                    Food food = restaurunt.getFoodByName(matcher2.group(2));
                    customerMenu.getCuurentCustomer().getCart().addFoodTocart(food, matcher2.group(1), amount);
                    return ("add to cart successful\n");
                }
            } else {
                Food food = restaurunt.getFoodByName(matcher.group(2));

                customerMenu.getCuurentCustomer().getCart().addFoodTocart(food, matcher.group(1), 1);
                return ("add to cart successful\n");
            }
        } else if (i == 3) {
            Food food = customerMenu.getCuurentCustomer().getCart().getFoodByNameAndRestuarantName(matcher.group(1),
                    matcher.group(2));
            if (food.getName() == null) {
                return ("remove from cart failed: not in cart\n");

            } else {
                Matcher matcher2 = subPatterns[3].matcher(userInput);
                if (matcher2.find()) {
                    int amount = Integer.parseInt(matcher2.group(4));
                    if (amount <= 0) {
                        return ("remove from cart failed: invalid number\n");
                    } else {
                        int existence = customerMenu.getCuurentCustomer().getCart().getFoodMaxExistenceAmount(food,
                                matcher2.group(1));
                        if (amount > existence) {
                            return ("remove from cart failed: not enough food in cart\n");
                        } else if (existence == amount) {
                            customerMenu.getCuurentCustomer().getCart().removeFromOrder(food, matcher2.group(1));
                            return ("remove from cart successful\n");
                        } else if (existence > amount) {
                            customerMenu.getCuurentCustomer().getCart().decreaseFoodAmountFromOrder(food,
                                    matcher2.group(1), amount);
                            return ("remove from cart successful\n");
                        }
                    }
                } else {
                    int amount = 1;
                    int existence = customerMenu.getCuurentCustomer().getCart().getFoodMaxExistenceAmount(food,
                            matcher.group(1));
                    if (amount > existence) {
                        return "";
                    } else if (existence == amount) {
                        customerMenu.getCuurentCustomer().getCart().removeFromOrder(food, matcher.group(1));
                        return ("remove from cart successful\n");
                    } else if (existence > amount) {
                        customerMenu.getCuurentCustomer().getCart().decreaseFoodAmountFromOrder(food, matcher.group(1),
                                amount);
                        return ("remove from cart successful\n");
                    }
                }
            }
        } else if (i == 4) {
            String[][] strings = customerMenu.getCuurentCustomer().getCart().getAllFoodString();

            return (showCustomerCarts(strings) + "Total: " + customerMenu.getCuurentCustomer().getCart().getTotalPrice()+ "\n");
        } else if (i == 5) {
            return discounts(customerMenu.getCuurentCustomer().getDiscount());
        } else if (i == 6) {
            Matcher matcher2 = subPatterns[4].matcher(userInput);
            int customerBalance;
            if (matcher2.find()) {
                DiscountCode discountCode = customerMenu.getCuurentCustomer().getDiscountByName(matcher2.group(2));
                if (discountCode.getName() == null) {
                    return ("purchase failed: invalid discount code\n");

                } else {
                    customerBalance = customerMenu.getCuurentCustomer().getBalance();
                    int discountAmount = discountCode.getAmount();
                    int totalPrice = customerMenu.getCuurentCustomer().getCart().getTotalPrice() - discountAmount;
                    if (totalPrice > customerBalance) {
                        return ("purchase failed: inadequate money\n");
                    } else {

                        customerMenu.getCuurentCustomer().removeDiscountCode(discountCode);
                        this.dataBase.removeDiscountCode(discountCode);
                        decreaseBalanceFromCustomer(totalPrice);
                        purchase();
                        return ("purchase successful\n");
                    }
                }
            } else {
                customerBalance = customerMenu.getCuurentCustomer().getBalance();
                int totalPrice = customerMenu.getCuurentCustomer().getCart().getTotalPrice();
                if (totalPrice > customerBalance) {
                    return ("purchase failed: inadequate money\n");
                } else {
                    decreaseBalanceFromCustomer(totalPrice);
                    purchase();
                    return ("purchase successful\n");
                }
            }
        } else if (i == 7) {
            this.customerMenu.logoutStatus = true;
            return "";
        } else if (i == 8) {
            return (customerMenu.getMenuName() + "\n");

        } else if (i == 9) {
            int amount = Integer.parseInt(matcher.group(1));
            if (amount <= 0) {
                return ("charge account failed: invalid cost or price\n");
            } else {
                customerMenu.getCuurentCustomer().chargeBalance(amount);
                return ("charge account successful\n");

            }
        } else if (i == 10) {
            return ((Integer) customerMenu.getCuurentCustomer().getBalance()).toString() + "\n";
        }
        return "";
    }

    public String restaurantsList(ArrayList restauranList) {
        String restuarants = new String();
        for (int i = 0; i < restauranList.size(); i++) {
            restuarants += ((i + 1) + ") " + ((Restaurunt) restauranList.get(i)).getUsername() + ": type="
                    + ((Restaurunt) restauranList.get(i)).getType() + "\n");
        }
        return restuarants;
    }

    public String menuAllCategory(Restaurunt restaurunt) {
        ArrayList starterFoodList = ((Category) restaurunt.getCategorys().get(0)).getFoods();
        ArrayList entreeFoodList = ((Category) restaurunt.getCategorys().get(1)).getFoods();
        ArrayList dessertFoodList = ((Category) restaurunt.getCategorys().get(2)).getFoods();

        String menuAllCategory = new String();
        menuAllCategory += ("<< STARTER >>\n");
        for (int i = 0; i < starterFoodList.size(); i++) {
            menuAllCategory += (((Food) starterFoodList.get(i)).getName() + " | price="
                    + ((Food) starterFoodList.get(i)).getPrice() + "\n");
        }

        menuAllCategory += ("<< ENTREE >>\n");
        for (int i = 0; i < entreeFoodList.size(); i++) {
            menuAllCategory += (((Food) entreeFoodList.get(i)).getName() + " | price="
                    + ((Food) entreeFoodList.get(i)).getPrice() + "\n");
        }

        menuAllCategory += ("<< DESSERT >>\n");
        for (int i = 0; i < dessertFoodList.size(); i++) {
            menuAllCategory += (((Food) dessertFoodList.get(i)).getName() + " | price="
                    + ((Food) dessertFoodList.get(i)).getPrice() + "\n");
        }
        return menuAllCategory;
    }

    public String speceficCategoryMenu(Restaurunt restaurunt, String category) {
        ArrayList starterFoodList = ((Category) restaurunt.getCategorys().get(0)).getFoods();
        ArrayList entreeFoodList = ((Category) restaurunt.getCategorys().get(1)).getFoods();
        ArrayList dessertFoodList = ((Category) restaurunt.getCategorys().get(2)).getFoods();
        String categoryProducts = new String();

        if (category.equals("starter")) {
            for (int i = 0; i < starterFoodList.size(); i++) {
                categoryProducts += (((Food) starterFoodList.get(i)).getName() + " | price="
                        + ((Food) starterFoodList.get(i)).getPrice() + "\n");
            }
        } else if (category.equals("entree")) {
            for (int i = 0; i < entreeFoodList.size(); i++) {
                categoryProducts += (((Food) entreeFoodList.get(i)).getName() + " | price="
                        + ((Food) entreeFoodList.get(i)).getPrice() + "\n");
            }
        } else if (category.equals("dessert")) {
            for (int i = 0; i < dessertFoodList.size(); i++) {
                categoryProducts += (((Food) dessertFoodList.get(i)).getName() + " | price="
                        + ((Food) dessertFoodList.get(i)).getPrice() + "\n");
            }
        }
        return categoryProducts;

    }

    private String showCustomerCarts(String[][] strings) {
        String allString = new String();
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[i].length; j++) {
                allString += (strings[i][j] + "\n");
            }
        }
        return allString;
    }

    private String discounts(ArrayList discounts) {
        String alldDiscounts = new String();
        for (int i = 0; i < discounts.size(); i++) {
            alldDiscounts += ((i + 1) + ") " + ((DiscountCode) discounts.get(i)).getName() + " | amount="
                    + ((DiscountCode) discounts.get(i)).getAmount() + "\n");
        }
        return alldDiscounts;
    }

    private void decreaseBalanceFromCustomer(int totalPrice) {
        this.customerMenu.getCuurentCustomer().decreaseBalance(totalPrice);
    }

    private void purchase() {
        customerMenu.getCuurentCustomer().getCart().setRestaurantsBenefit();
        customerMenu.getCuurentCustomer().getCart().emptyOrders();
    }
}
