package model;
import java.util.ArrayList;

import controller.Cart;

public class Customer extends User {
    private ArrayList discount = new ArrayList<DiscountCode>();
    private Cart cart;
    private int balance;

    public Customer(String userName, String password) {
        super(userName, password);
        this.balance = 0;
        this.cart=new Cart();
    }

    public void setDiscount(DiscountCode discountCode) {
        this.discount.add(discountCode);
    }

    public ArrayList getDiscount() {
        return discount;
    }

    public int getBalance() {
        return balance;
    }

    public Cart getCart() {
        return cart;
    }

    public void chargeBalance(int amount) {
        this.balance += amount;
    }

    public DiscountCode getDiscountByName(String name) {
        DiscountCode discountCode = new DiscountCode(null, -1, null);
        for (int i = 0; i < this.discount.size(); i++) {
            if (((DiscountCode) discount.get(i)).getName().equals(name)) {
                return ((DiscountCode) discount.get(i));
            }
        }
        return discountCode;
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        if(!this.discount.contains(discountCode)){
            System.out.println("OOPS Code Dose Not Exist!!!");
        }else{
            this.discount.remove(discountCode);
        }
    }
    public void decreaseBalance(int amount) {
        this.balance-=amount;
    }

}
