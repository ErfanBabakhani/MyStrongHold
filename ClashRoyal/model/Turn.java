package model;
import java.util.ArrayList;

public class Turn {
    private int remainingCart;
    private int remainingMoves;
    private String turnUserName;

    public Turn() {
        this.remainingCart = 1;
        this.remainingMoves = 3;
        setTurnUserName("host");
    }

    public int getRemainingCart() {
        return remainingCart;
    }

    public int getRemainingMoves() {
        return remainingMoves;
    }

    public void decreaseRemainingMoves() {
        if (this.remainingMoves > 0) {
            this.remainingMoves--;
        }
    }

    public void decreaseRemainingCart() {
        if (this.remainingCart > 0) {
            this.remainingCart--;
        }
    }

    public void setTurnUserName(String turnUserName) {
        this.turnUserName = turnUserName;
    }

    public String getTurnUserName() {
        return turnUserName;
    }

    public void setRemainingCart(int remainingCart) {
        this.remainingCart = remainingCart;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }
}
