package org.example.controller;

import org.example.enums.StoreGood;
import org.example.model.ActiveGame;
import org.example.model.DataBase;
import org.example.model.GameInfo.Game;
import org.example.model.GameInfo.Good;
import org.example.model.GameInfo.Government;
import org.example.model.GameInfo.Store;
import org.example.view.GameMenu;
import org.example.view.Menu;
import org.example.view.ShopMenu;

import java.net.Socket;
import java.util.regex.Matcher;

public class ShopController {
    private GameController gameController;

    public ShopController(GameController gameController) {
        this.gameController = gameController;
    }

    public String showPriceList() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < StoreGood.values().length; i++) {
            out.append("<< ").append(StoreGood.values()[i].name()).append(" >> : \n");
            for (Good tarGood : StoreGood.values()[i].getGoodList()) {
                out.append(tarGood.getGoodName()).append(" : ").append(tarGood.getPrice()).append("$").append("\n");
            }
        }
        return out.toString();
    }

    public String buy(Matcher matcher) {
        String goodName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        String storeType = findStoreTypeByGood(goodName);
        Good tar = findGood(goodName);
        if (tar == null)
            return "Invalid good name";
        int price = tar.getPrice() * amount;
        if (gameController.getCurrentGovernment().getCoin() < price)
            return "Not Enough Money !";
        if (gameController.getTheAllSpace(storeType) < amount)
            return "Not Enough Space";
        gameController.increaseElementOfStore(storeType, goodName, amount);
        gameController.getCurrentGovernment().setCoin(gameController.getCurrentGovernment().getCoin() - amount);
        return "We buy " + "<< " + goodName + " >> : " + amount + " successfully";
    }

    private String findStoreTypeByGood(String goodName) {
        for (int i = 0; i < StoreGood.values().length; i++) {
            for (Good tarGood : StoreGood.values()[i].getGoodList()) {
                if (tarGood.getGoodName().equals(goodName))
                    return StoreGood.values()[i].name();
            }
        }
        return null;
    }

    private Good findGood(String goodName) {
        for (int i = 0; i < StoreGood.values().length; i++) {
            for (Good tarGood : StoreGood.values()[i].getGoodList()) {
                if (tarGood.getGoodName().equals(goodName))
                    return tarGood;
            }
        }
        return null;
    }

    public String sell(Matcher matcher) {
        String goodName = matcher.group("name");
        int amount = Integer.parseInt(matcher.group("amount"));
        String storeType = findStoreTypeByGood(goodName);
        Good tar = findGood(goodName);
        if (tar == null)
            return "Invalid good name";
        int price = tar.getPrice() * amount;
        if (gameController.getAllElementFromStore(storeType, goodName) < amount)
            return "You do not have enough " + goodName;
        gameController.decreaseElementFromStore(storeType, goodName, amount);
        gameController.getCurrentGovernment().setCoin(gameController.getCurrentGovernment().getCoin() + price);
        return "We sell " + "<< " + goodName + " >> : " + amount + " successfully";
    }

    public void start(Socket socket) {
        ShopMenu shopMenu = new ShopMenu();
        shopMenu.setShopController(this);
        shopMenu.run(socket);
    }

}
