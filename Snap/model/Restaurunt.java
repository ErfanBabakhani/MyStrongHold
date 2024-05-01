package model;
import java.util.ArrayList;

public class Restaurunt {
    private String username;
    private String type;
    private int balance;
    public ArrayList categorys = new ArrayList<Category>();
    {
        categorys.add(new Category("starter"));
        categorys.add(new Category("entree"));
        categorys.add(new Category("dessert"));
    }

    public Restaurunt(String username, String type) {
        this.username = username;
        this.type = type;
        this.balance = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public int getBalance() {
        return balance;
    }

    public Food getFoodByName(String name) {
        Food food = new Food(null, -1, -1);
        for (int i = 0; i < categorys.size(); i++) {
            ArrayList foodList = ((Category) categorys.get(i)).getFoods();
            for (int j = 0; j < foodList.size(); j++) {
                if (((Food) foodList.get(j)).getName().equals(name)) {
                    return ((Food) foodList.get(j));
                }
            }
        }
        return food;

    }

    public void removeFoodByName(String name) {
        for (int i = 0; i < categorys.size(); i++) {
            ArrayList foodList = ((Category) categorys.get(i)).getFoods();
            for (int j = 0; j < foodList.size(); j++) {
                if (((Food) foodList.get(j)).getName().equals(name)) {
                    ((Category) categorys.get(i)).getFoods().remove(((Food) foodList.get(j)));
                    return;
                }
            }
        }
        System.out.println("OPSFoodNotFound");
        return;
    }

    public void addFood(Food food, String cateegoryName) {
        if (cateegoryName.equals("starter")) {
            ((Category) categorys.get(0)).getFoods().add(food);
        } else if (cateegoryName.equals("entree")) {
            ((Category) categorys.get(1)).getFoods().add(food);

        } else if (cateegoryName.equals("dessert")) {
            ((Category) categorys.get(2)).getFoods().add(food);
        } else {
            System.out.println("OOPsinvalid Category");
            return;
        }
    }

    public void chargeBalance(int amount) {
        this.balance += amount;
    }

    public ArrayList getCategorys() {
        return categorys;
    }

    public void increaseBalance(int amount) {
        this.balance += amount;
    }

}
