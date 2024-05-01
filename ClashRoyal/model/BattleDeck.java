package model;
import java.util.ArrayList;

public class BattleDeck {

    private User user;
    private ArrayList allCarts = new ArrayList<Cart>();

    public BattleDeck(User user) {
        setUser(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void addCart(Cart cart) {
        this.allCarts.add(cart);
    }

    public ArrayList getAllCarts() {
        return allCarts;
    }

    public Cart getCartByName(String name) {
        
        for (int i = 0; i < this.allCarts.size(); i++) {
            if (((Cart) this.allCarts.get(i)).getCartName().equals(name)) {
                return ((Cart) this.allCarts.get(i));
            }
        }
        return null;
    }

    public void removeCartByname(String name) {
        this.allCarts.remove(getCartByName(name));
    }

    

}
