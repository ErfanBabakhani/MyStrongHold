package model;
import java.util.ArrayList;
public class User {
    private String username;
    private String password;
    private int gold;
    private int experience;
    private int level;
    private BattleDeck battleDeck = new BattleDeck(this);
    private ArrayList towers = new ArrayList<Tower>(); // 0 is left and 1 main and 2 right
    private ArrayList buyedCarts = new ArrayList<Cart>();

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
        setExperience(0);
        setLevel(1);
        setGold(100);
        setTower();
        addCartToBattleDeck(new Cart("Fireball",this));
        addCartToBattleDeck(new Cart("Barbarian",this));
        addCartToBuyedCart(new Cart("Fireball",this));
        addCartToBuyedCart(new Cart("Barbarian",this));

    }

    public void name() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getGold() {
        return gold;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public BattleDeck getBattleDeck() {
        return battleDeck;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Cart getBuyedCartByName(String name) {
        // Cart cart = new Cart(null, 0, 0);
        for (int i = 0; i < this.buyedCarts.size(); i++) {
            if (((Cart) buyedCarts.get(i)).getCartName().equals(name)) {
                return ((Cart) buyedCarts.get(i));
            }
        }
        return null;
    }

    private void setTower() {
        this.towers.add(new Tower("left", this));
        this.towers.add(new Tower("middle", this));
        this.towers.add(new Tower("right", this));
    }

    public ArrayList getTowers() {
        return towers;
    }

    public void addCartToBattleDeck(Cart cart) {
        this.battleDeck.addCart(cart);
    }

    public void addCartToBuyedCart(Cart cart) {
        this.buyedCarts.add(cart);
    }

    public int needExperienceToLevelUp() {
        return this.level * this.level * 160;
    }

    public void updateTowersHitpoint() {
        for (int i = 0; i < this.towers.size(); i++) {
            ((Tower) towers.get(i)).setTheTowerMaxHitpoint();
        }
    }

    public void updateTowersFightStrong() {
        for (int i = 0; i < this.towers.size(); i++) {
            ((Tower) towers.get(i)).setTowerFightPower();
        }
    }
    
    public void removeFromBuyedCart(Cart cart) {
        buyedCarts.remove(cart);
    }
}
