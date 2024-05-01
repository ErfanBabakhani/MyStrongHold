package model;
import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private String restuarantName;
    // private int totalPrice = 0;
    // private int totalCost = 0;
    private ArrayList foodList = new ArrayList<Food>();
    private HashMap foodToNum = new HashMap<Food, Integer>();
    private DataBase dataBase = DataBase.getInstance();

    public Order(String restuarantName, Food food, int num) {
        this.restuarantName = restuarantName;
        this.addFood(food, num);
    }

    public void addFood(Food food, int num) {
        if (this.foodToNum.containsKey(food)) {
            increaseFoodAmount(food, num);
            return;
        } else {
            this.foodList.add(food);
            this.foodToNum.put(food, num);

        }

    }

    public int getNumberOfFood(Food food) {
        if (!foodList.contains(food)) {
            return -1;
        }
        return ((int) foodToNum.get(food));
    }

    public Food getFoodByName(String name) {
        Food food = new Food(null, -1, -1);
        for (int i = 0; i < foodList.size(); i++) {
            if (((Food) foodList.get(i)).getName().equals(name)) {
                return ((Food) foodList.get(i));
            }
        }
        return food;
    }

    public void increaseFoodAmount(Food food, int num) {
        int firstAmount = ((int) this.foodToNum.get(food));
        this.foodToNum.remove(food);
        int seccondAmount = firstAmount + num;
        this.foodToNum.put(food, seccondAmount);


    }

    public void decreaseFoodAmount(Food food, int num) {
        int firstAmount = ((int) this.foodToNum.get(food));
        this.foodToNum.remove(food);
        int seccondAmount = firstAmount - num;
        this.foodToNum.put(food, seccondAmount);

    }

    public void removeFood(Food food) {
        this.foodList.remove(food);
        this.foodToNum.remove(food);
    }

    public String getRestuarantName() {
        return restuarantName;
    }

    public ArrayList getFoodList() {
        return foodList;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < foodList.size(); i++) {
            totalPrice += ((int) foodToNum.get((Food) foodList.get(i))) * (((Food) foodList.get(i))).getPrice();
        }
        return totalPrice;
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (int i = 0; i < foodList.size(); i++) {
            totalCost += ((int) foodToNum.get((Food) foodList.get(i))) * (((Food) foodList.get(i))).getCost();
        }
        return totalCost;
    }

    public int printAllFoods(String[] scr, int start) {
        int totalPrice = 0;
        int i;
        for (i = 0; i < foodList.size(); i++) {
            totalPrice = ((int) foodToNum.get((Food) foodList.get(i))) * (((Food) foodList.get(i))).getPrice();
            scr[i] = (start + i) + ") " + ((Food) foodList.get(i)).getName() + " | restaurant=" + this.restuarantName
                    + " price=" + totalPrice;
        }
        return i;
    }

    public void setResturantBenefit() {
        Restaurunt restaurunt = this.dataBase.getRestauruntByUsername(restuarantName);
        restaurunt.increaseBalance(this.getTotalPrice() - this.getTotalCost());
    }
}
