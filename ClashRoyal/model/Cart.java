package model;

public class Cart {

    private User owner;
    private String cartName;
    private int price;
    private int line = -10;
    private int row;
    private int hitpoint;
    private int maxHitpoint;
    private int fightStrong;

    public Cart(String cartName, User owner) {
        setOwner(owner);
        if (cartName.equals("Fireball")) {
            setCartName(cartName);
            setPrice(100);
            setFightStrong(1600);
            setHitpoint(0);
            setMaxHitpoint(0);
        } else if (cartName.equals("Heal")) {
            setCartName(cartName);
            setPrice(150);
            setFightStrong(1000);
            setHitpoint(2);
            setMaxHitpoint(2);
        } else if (cartName.equals("Barbarian")) {
            setCartName(cartName);
            setPrice(100);
            setFightStrong(900);
            setHitpoint(2000);
            setMaxHitpoint(2000);

        } else if (cartName.equals("Baby Dragon")) {
            setCartName(cartName);
            setPrice(200);
            setFightStrong(1500);
            setHitpoint(3500);
            setMaxHitpoint(3500);

        } else if (cartName.equals("Ice Wizard")) {

            setCartName(cartName);
            setPrice(180);
            setFightStrong(1200);
            setHitpoint(3300);
            setMaxHitpoint(3300);

        }
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public void setFightStrong(int fightStrong) {
        this.fightStrong = fightStrong;
    }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public String getCartName() {
        return cartName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMaxHitpoint(int maxHitpoint) {
        this.maxHitpoint = maxHitpoint;
    }

    public int getPrice() {
        return price;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public int getFightStrong() {
        return fightStrong;
    }

    public int getMaxHitpoint() {
        return maxHitpoint;
    }

    private void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

}
