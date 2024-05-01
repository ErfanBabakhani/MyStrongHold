package controller;

import model.*;

public class BattleController extends MainController {

    private BattleDeck battleDeck;

    public void BattleController(BattleDeck battleDeck) {
        setBattleDeck(battleDeck);
    }

    public Cart getCartByName(String name) {

        for (int i = 0; i < this.battleDeck.getAllCarts().size(); i++) {
            if (((Cart) this.battleDeck.getAllCarts().get(i)).getCartName().equals(name)) {
                return ((Cart) this.battleDeck.getAllCarts().get(i));
            }
        }
        return null;
    }

    private void setBattleDeck(BattleDeck battleDeck) {
        this.battleDeck = battleDeck;
    }
}