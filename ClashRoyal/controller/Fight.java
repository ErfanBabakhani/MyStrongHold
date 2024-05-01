package controller;
import java.util.ArrayList;
import model.Cart;
import model.Map;
import model.Tower;

public class Fight extends MainController{
    private Map map;

    public Fight(Map map) {
        setMap(map);
    }

    private void setMap(Map map) {
        this.map = map;
    }

    public void runFight() {
        fightBetweenTroops(map.getHostTroopCarts(), map.getGuestTroopCarts());
        fightBetweenTowerAndEnemyCart(map.getHostTroopCarts(), map.getGuestTroopCarts());
        runHealCarts();

    }

    public void runAndRemoveFireBall() {
        runFireBall();
        removeAllDeadFireBallCarts();
    }

    public void removeDeadCarts() {
        removeAllDeadTroopCarts();
        removeAllDeadHealCarts();
    }

    public void fightBetweenTroops(ArrayList ourTroops, ArrayList enemyTroops) {

        for (int i = 0; i < ourTroops.size(); i++) {
            for (int j = 0; j < enemyTroops.size(); j++) {
                if (isCartsIntheSameHome(((Cart) ourTroops.get(i)), ((Cart) enemyTroops.get(j)))) {
                    setCartHitpoints(((Cart) ourTroops.get(i)), ((Cart) enemyTroops.get(j)));
                }
            }
        }
    }

    public void fightBetweenTowerAndEnemyCart(ArrayList hostTroops, ArrayList guestTroops) {
        for (int i = 0; i < guestTroops.size(); i++) {
            if (isCartIntheTower(1, ((Cart) guestTroops.get(i)))) {
                if (isDeadTower(((Tower) map.getHostTowers().get(((Cart) guestTroops.get(i)).getLine() - 1)))) {
                    continue;
                }
                setCartAndTowerHitpoint(findTowerByEnemyLine("host", (Cart) guestTroops.get(i)),
                        (Cart) guestTroops.get(i));
            }
        }
        for (int i = 0; i < hostTroops.size(); i++) {
            if (isCartIntheTower(15, ((Cart) hostTroops.get(i)))) {
                if (isDeadTower(((Tower) map.getGuestTowers().get(((Cart) hostTroops.get(i)).getLine() - 1)))) {
                    continue;
                }
                setCartAndTowerHitpoint(findTowerByEnemyLine("guest", (Cart) hostTroops.get(i)),
                        (Cart) hostTroops.get(i));
            }
        }
    }

    private void runHealCarts() {
        for (int i = 0; i < map.getGuestHealCarts().size(); i++) {
            for (int j = 0; j < map.getGuestTroopCarts().size(); j++) {
                if (isCartsIntheSameHome(((Cart) map.getGuestHealCarts().get(i)),
                        ((Cart) map.getGuestTroopCarts().get(j)))) {
                    increaseHitipointByHeal(((Cart) map.getGuestTroopCarts().get(j)));
                }
            }
            // decreasHealhitpoint(((Cart) map.getGuestHealCarts().get(i)));
        }
        for (int i = 0; i < map.getHostHealCarts().size(); i++) {
            for (int j = 0; j < map.getHostTroopCarts().size(); j++) {
                if (isCartsIntheSameHome(((Cart) map.getHostHealCarts().get(i)),
                        ((Cart) map.getHostTroopCarts().get(j)))) {
                    increaseHitipointByHeal(((Cart) map.getHostTroopCarts().get(j)));
                }
            }
            // decreasHealhitpoint(((Cart) map.getHostHealCarts().get(i)));
        }
    }

    private void runFireBall() {
        for (int i = 0; i < map.getGuestFireBallCarts().size(); i++) {
            // System.out.println("!!!!");
            decreaseHitpointFireBall(findTowerByEnemyLine("host", ((Cart) map.getGuestFireBallCarts().get(i))));
        }
        for (int i = 0; i < map.getHostFireBallCarts().size(); i++) {
            // System.out.println("?????");
            decreaseHitpointFireBall(findTowerByEnemyLine("guest", ((Cart) map.getHostFireBallCarts().get(i))));

        }
    }

    private boolean isCartsIntheSameHome(Cart a, Cart b) {

        if (a.getLine() == b.getLine() && a.getRow() == b.getRow()) {
            return true;
        }

        return false;
    }

    private int fightStrongth(Cart troopA, Cart troopB) {
        int troopAStrong = troopA.getFightStrong();
        int troopBStrong = troopB.getFightStrong();

        // if (troopA.getCartName().equals("Barbarian") && troopB.getCartName().equals("Baby Dragon")) {
        //     return troopB.getFightStrong();
        // } else if (troopA.getCartName().equals("Barbarian") && troopB.getCartName().equals("Baby Dragon:")) {
        //     return troopA.getFightStrong();
        // }

        return Math.abs(troopAStrong - troopBStrong);
    }

    private void setCartHitpoints(Cart carta, Cart cartb) {
        if (carta.getFightStrong() > cartb.getFightStrong()) {
            cartb.setHitpoint(cartb.getHitpoint() - fightStrongth(carta, cartb));
        } else if (carta.getFightStrong() < cartb.getFightStrong()) {
            carta.setHitpoint(carta.getHitpoint() - fightStrongth(carta, cartb));
        }
    }

    private boolean isCartIntheTower(int towerRow, Cart attacker) {

        if (attacker.getRow() == towerRow) {
            return true;
        }
        return false;
    }

    private void setCartAndTowerHitpoint(Tower targetTower, Cart enemy) {
        int targetFightPower = targetTower.getTowerFightPower();
        int enemyFightPower = enemy.getFightStrong();

        enemy.setHitpoint(enemy.getHitpoint() - targetFightPower);
        targetTower.setHitpoint(targetTower.getHitpoint() - enemyFightPower);
    }

    private Tower findTowerByEnemyLine(String userRole, Cart enemy) {
        int enemyLine = enemy.getLine();
        Tower tower = null;
        if (userRole.equals("guest")) {
            tower = (Tower) map.getGuestTowers().get(enemyLine - 1);
        } else if (userRole.equals("host")) {
            tower = (Tower) map.getHostTowers().get(enemyLine - 1);
        }
        return tower;
    }

    private void increaseHitipointByHeal(Cart targetCart) {
        targetCart.setHitpoint(targetCart.getHitpoint() + 1000);
        if (targetCart.getHitpoint() > targetCart.getMaxHitpoint()) {
            targetCart.setHitpoint(targetCart.getMaxHitpoint());
        }

    }

    private void decreaseHitpointFireBall(Tower targetTower) {
        targetTower.setHitpoint(targetTower.getHitpoint() - 1600);
    }

    public void decreasHealhitpoints(String userRole) {
        ArrayList healCarts = new ArrayList<Cart>();
        if (userRole.equals("host")) {
            healCarts = map.getHostHealCarts();

        } else if (userRole.equals("guest")) {
            healCarts = map.getGuestHealCarts();
        }
        for (int i = 0; i < healCarts.size(); i++) {
            ((Cart) healCarts.get(i)).setHitpoint(((Cart) healCarts.get(i)).getHitpoint() - 1);
        }

    }

    private boolean isDeadCart(Cart cart) {
        if (cart.getHitpoint() <= 0)
            return true;
        return false;
    }

    private boolean isDeadTower(Tower tower) {
        if (tower.getHitpoint() <= 0)
            return true;
        return false;
    }

    private void removeAllDeadTroopCarts() {
        for (int i = 0; i < map.getGuestTroopCarts().size(); i++) {
            if (isDeadCart(((Cart) map.getGuestTroopCarts().get(i)))) {
                map.removeTroopCartToMap("guest", ((Cart) map.getGuestTroopCarts().get(i)));
            }
        }
        for (int i = 0; i < map.getHostTroopCarts().size(); i++) {
            if (isDeadCart(((Cart) map.getHostTroopCarts().get(i)))) {
                map.removeTroopCartToMap("host", ((Cart) map.getHostTroopCarts().get(i)));
            }
        }
    }

    private void removeAllDeadHealCarts() {
        for (int i = 0; i < map.getGuestHealCarts().size(); i++) {
            if (isDeadCart(((Cart) map.getGuestHealCarts().get(i)))) {
                map.removeHealCart("gusest", ((Cart) map.getGuestHealCarts().get(i)));
            }
        }

        for (int i = 0; i < map.getHostHealCarts().size(); i++) {
            if (isDeadCart(((Cart) map.getHostHealCarts().get(i)))) {
                map.removeHealCart("host", ((Cart) map.getHostHealCarts().get(i)));
            }
        }
    }

    private void removeAllDeadFireBallCarts() {
        Cart cart = new Cart("Fireball",null);
        for (int i = 0; i < map.getGuestFireBallCarts().size(); i++) {
            if (isDeadCart(cart)) {
                map.removeFireBall("guest", ((Cart) map.getGuestFireBallCarts().get(i)));
            }
        }
        for (int i = 0; i < map.getHostFireBallCarts().size(); i++) {
            if (isDeadCart(cart)) {
                map.removeFireBall("host", ((Cart) map.getHostFireBallCarts().get(i)));
            }
        }
    }

    // private void removeAllDeadTowers() {
    // for (int i = 0; i < map.getGuestTowers().size(); i++) {
    // if (isDeadTower(((Tower) map.getGuestTowers().get(i)))) {
    // map.removeTowerFromMap("guest", ((Tower) map.getGuestTowers().get(i)));
    // }
    // }
    // for (int i = 0; i < map.getHostTowers().size(); i++) {
    // if (isDeadTower(((Tower) map.getHostTowers().get(i)))) {
    // map.removeTowerFromMap("host", ((Tower) map.getHostTowers().get(i)));
    // }
    // }
    // }

}
