package model;
import java.util.ArrayList;

public class Cart {
    ArrayList orderList = new ArrayList<Order>();

    private void addOrderToCart(String restuarantName, Food food, int firstFoodNum) {
        Order order = new Order(restuarantName, food, firstFoodNum);
        this.orderList.add(order);
    }

    public void addFoodTocart(Food food, String restuarantName, int amount) {
        Order order = getOrderByRestuarantName(restuarantName);
        if (order.getRestuarantName()!=null&&order.getRestuarantName().equals(restuarantName)) {
            order.addFood(food, amount);
        } else {
            addOrderToCart(restuarantName, food, amount);
        }
    }

    private Order getOrderByRestuarantName(String name) {
        Order order = new Order(null, null, -1);
        for (int i = 0; i < orderList.size(); i++) {
            if (((Order) orderList.get(i)).getRestuarantName().equals(name)) {
                return ((Order) orderList.get(i));
            }
        }
        return order;
    }

    public Food getFoodByNameAndRestuarantName(String restuarntName, String foodName) {
        Food food = new Food(null, -1, -1);
        Order order = getOrderByRestuarantName(restuarntName);
        if (order.getRestuarantName() == null) {
            return food;
        } else {
            return order.getFoodByName(foodName);
        }
    }

    public void removeFromOrder(Food food, String restuarantName) {
        Order order = getOrderByRestuarantName(restuarantName);

        order.removeFood(food);
    }

    public void decreaseFoodAmountFromOrder(Food food, String restuarantName, int amount) {
        Order order = getOrderByRestuarantName(restuarantName);
        order.decreaseFoodAmount(food, amount);
    }

    public int getFoodMaxExistenceAmount(Food food, String restuarantName) {
        Order order = getOrderByRestuarantName(restuarantName);
        return order.getNumberOfFood(food);
    }
    public String[][] getAllFoodString() {
        int start=1;
        int first=orderList.size();
        int seccond;
        String[][]strings=new String[first][];
        for (int i = 0; i < first; i++) {
            seccond=((Order)orderList.get(i)).getFoodList().size();
            strings[i]=new String[seccond];
            start+=((Order)orderList.get(i)).printAllFoods(strings[i], start);
        }
        return strings;
    }
    public int getTotalPrice() {
        int totalPrice=0;
        for (int i = 0; i < orderList.size(); i++) {
            totalPrice+=((Order)orderList.get(i)).getTotalPrice();
        }
        return totalPrice;
    }
    public int getTotalCost() {
        int totalCost=0;
        for (int i = 0; i < orderList.size(); i++) {
            totalCost+=((Order)orderList.get(i)).getTotalCost();
        }
        return totalCost;
    }
    public void setRestaurantsBenefit() {
        for (int i = 0; i < orderList.size(); i++) {
            ((Order)orderList.get(i)).setResturantBenefit();
        }   
    }
    public void emptyOrders() {
        this.orderList.removeAll(orderList);
    }
}
