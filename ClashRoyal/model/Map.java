package model;

import java.util.ArrayList;

public class Map {

    private User hosetUser;
    private User guestUser;
    private ArrayList allCarts = new ArrayList<Cart>();

    private ArrayList hostTowers = new ArrayList<Tower>();
    private ArrayList hostTroopCarts = new ArrayList<Cart>();
    private ArrayList hostFireBallCarts = new ArrayList<Cart>();
    private ArrayList hostHealCarts = new ArrayList<Cart>();

    private ArrayList guestTowers = new ArrayList<Tower>();
    private ArrayList guestTroopCarts = new ArrayList<Cart>();
    private ArrayList guestFireBallCarts = new ArrayList<Cart>();
    private ArrayList guestHealCarts = new ArrayList<Cart>();

    public Map(User hostUser, User guestUser) {
        setHosetUser(hostUser);
        setGuestUser(guestUser);
        setGuestTowers();
        setHostTowers();
    }

    public void setHosetUser(User hosetUser) {
        this.hosetUser = hosetUser;
    }

    public void setGuestUser(User guestUser) {
        this.guestUser = guestUser;
    }

    private void setHostTowers() {
        this.hostTowers.addAll(this.hosetUser.getTowers());
    }

    private void setGuestTowers() {
        this.guestTowers.addAll(this.guestUser.getTowers());
    }

    public ArrayList getGuestTroopCarts() {
        return guestTroopCarts;
    }

    public ArrayList getHostTroopCarts() {
        return hostTroopCarts;
    }

    public void addTroopCartToMap(String userRole, Cart cart) {
        if (userRole.equals("host")) {
            hostTroopCarts.add(cart);
            allCarts.add(cart);
        } else if (userRole.equals("guest")) {
            guestTroopCarts.add(cart);
            allCarts.add(cart);
        }
    }

    public void runFireBall(String userRole, Cart cart) {

        if (userRole.equals("host")) {

            hostFireBallCarts.add(cart);
        } else if (userRole.equals("guest")) {
            guestFireBallCarts.add(cart);

        }
    }

    public void runHeal(String userRole, Cart cart) {
        if (userRole.equals("host")) {
            hostHealCarts.add(cart);
            allCarts.add(cart);
        } else if (userRole.equals("guest")) {
            guestHealCarts.add(cart);
            allCarts.add(cart);
        }
    }

    public void removeHealCart(String userRole, Cart cart) {
        if (userRole.equals("host")) {
            hostHealCarts.remove(cart);
            allCarts.remove(cart);
        } else if (userRole.equals("guest")) {
            guestHealCarts.remove(cart);
            allCarts.remove(cart);
        }
    }

    public void removeFireBall(String userRole, Cart cart) {
        if (userRole.equals("host")) {
            hostFireBallCarts.remove(cart);
            allCarts.remove(cart);
        } else if (userRole.equals("guest")) {
            guestFireBallCarts.remove(cart);
            allCarts.remove(cart);
        }
    }

    public void removeTroopCartToMap(String userRole, Cart cart) {
        if (userRole.equals("host")) {
            hostTroopCarts.remove(cart);
            allCarts.remove(cart);
        } else if (userRole.equals("guest")) {
            guestTroopCarts.remove(cart);
            allCarts.remove(cart);
        }
    }

    public User getHosetUser() {
        return hosetUser;
    }

    public User getGuestUser() {
        return guestUser;
    }

    public ArrayList getAllCarts() {
        return allCarts;
    }

    public ArrayList getGuestHealCarts() {
        return guestHealCarts;
    }

    public ArrayList getHostFireBallCarts() {
        return hostFireBallCarts;
    }

    public ArrayList getGuestFireBallCarts() {
        return guestFireBallCarts;
    }

    public ArrayList getHostHealCarts() {
        return hostHealCarts;
    }

    public ArrayList getGuestTowers() {
        return guestTowers;
    }

    public ArrayList getHostTowers() {
        return hostTowers;
    }

}
