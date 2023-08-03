package org.example.controller;

import org.example.model.DataBase;
import org.example.model.GameInfo.*;
import org.example.view.ShopMenu;

import java.net.Socket;
import java.util.regex.Matcher;

import static org.example.view.Menu.isMatched;

public class ShopController {

    private Government currentGovernment;

    public ShopController(Government currentGovernment) {
        this.currentGovernment = currentGovernment;
    }

    public void start(Socket socket) {
        ShopMenu shopMenu = new ShopMenu();
        shopMenu.setShopController(this);
        shopMenu.run(socket);
    }

    public String showPriceList() {
        String txt = "";
        Good Apple = new Good("Apple", 10.0, 5.0);
        Good Meat = new Good("Apple", 10.0, 5.0);
        Good Cheese = new Good("Apple", 10.0, 5.0);
        Good Bread = new Good("Apple", 10.0, 5.0);
        for (Granery granery : currentGovernment.getGraneries()) {
            Apple.setNumber(Apple.getNumber() + granery.getElements("Apple"));
            Meat.setNumber(Meat.getNumber() + granery.getElements("Meat"));
            Cheese.setNumber(Cheese.getNumber() + granery.getElements("Cheese"));
            Bread.setNumber(Bread.getNumber() + granery.getElements("Bread"));
        }
        txt += Apple.goodToText();
        txt += Meat.goodToText();
        txt += Cheese.goodToText();
        txt += Bread.goodToText();

        Good stone = new Good("stone", 30.0, 20.0);
        Good wood = new Good("wood", 30.0, 20.0);
        Good iron = new Good("iron", 30.0, 20.0);
        Good pitch = new Good("pitch", 30.0, 20.0);
        Good wheat = new Good("wheat", 30.0, 20.0);
        Good flour = new Good("flour", 30.0, 20.0);

        for (StockPile stockPile : currentGovernment.getStockPiles()) {
            stone.setNumber(stone.getNumber() + stockPile.getElements("stone"));
            wood.setNumber(wood.getNumber() + stockPile.getElements("wood"));
            iron.setNumber(iron.getNumber() + stockPile.getElements("iron"));
            pitch.setNumber(pitch.getNumber() + stockPile.getElements("pitch"));
            wheat.setNumber(wheat.getNumber() + stockPile.getElements("wheat"));
            flour.setNumber(flour.getNumber() + stockPile.getElements("flour"));
        }
        txt += stone.goodToText();
        txt += wood.goodToText();
        txt += iron.goodToText();
        txt += pitch.goodToText();
        txt += wheat.goodToText();
        txt += flour.goodToText();

        Good LeatherArmour = new Good("LeatherArmour", 60.0, 40.0);
        Good Mace = new Good("Mace", 60.0, 40.0);
        Good Sword = new Good("Sword", 60.0, 40.0);
        Good MetalArmour = new Good("MetalArmour", 60.0, 40.0);
        Good Bow = new Good("Bow", 60.0, 40.0);
        Good Crossbow = new Good("Crossbow", 60.0, 40.0);
        Good Spear = new Good("Spear", 60.0, 40.0);
        Good Pike = new Good("Pike", 60.0, 40.0);
        for (Armoury armoury : currentGovernment.getArmouries()) {
            LeatherArmour.setNumber(LeatherArmour.getNumber() + armoury.getElements("LeatherArmour"));
            Mace.setNumber(Mace.getNumber() + armoury.getElements("Mace"));
            Sword.setNumber(Sword.getNumber() + armoury.getElements("Sword"));
            MetalArmour.setNumber(MetalArmour.getNumber() + armoury.getElements("MetalArmour"));
            Bow.setNumber(Bow.getNumber() + armoury.getElements("Bow"));
            Crossbow.setNumber(Crossbow.getNumber() + armoury.getElements("Crossbow"));
            Spear.setNumber(Spear.getNumber() + armoury.getElements("Spear"));
            Pike.setNumber(Pike.getNumber() + armoury.getElements("Pike"));
        }

        txt += LeatherArmour.goodToText();
        txt += Mace.goodToText();
        txt += Sword.goodToText();
        txt += MetalArmour.goodToText();
        txt += Bow.goodToText();
        txt += Crossbow.goodToText();
        txt += Spear.goodToText();
        txt += Pike.goodToText();

        return txt;
    }

    public String buy(Matcher matcher) {
        String name = matcher.group("name");
        if (!DataBase.getGoods().contains(name))
            return "please enter a valid good name";
        int amount = (int) Double.parseDouble(matcher.group("amount"));
        String granery = "AppleMeatCheeseBread";
        String stockpile = "stonewoodironpitchwheatflour";
        String armoury = "LeatherArmourMaceSwordMetalArmourBowCrossbowSpearPike";
        Good good = new Good("good", 0.0, 0.0);
        if (currentGovernment.getCoin() < (amount * good.getGoodByName(name).getBuy())) {
            return "You have not enough coin!";
        } else if ((granery.contains(name) && (graneryCapacity(currentGovernment)) < amount) ||
                (armoury.contains(name) && (armouryCapacity(currentGovernment)) < amount) ||
                (stockpile.contains(name) && (stockpileCapacity(currentGovernment)) < amount)) {
            return "You have not enough space!";
        }
        currentGovernment.setCoin(currentGovernment.getCoin() - (amount * good.getGoodByName(name).getBuy()));
        good.getGoodByName(name).setNumber(good.getGoodByName(name).getNumber() + amount);
        if (granery.contains(name)) {
            for (int i = 0; i < currentGovernment.getGraneries().size(); i++) {
                if (currentGovernment.getGraneries().get(i).getCapacity() > amount) {
                    currentGovernment.getGraneries().get(i).changeCapacity((double) amount);
                    currentGovernment.getGraneries().get(i).setElements(name, currentGovernment.getGraneries().get(i).getElements(name) + amount);
                    break;
                } else {
                    currentGovernment.getGraneries().get(i).setElements(name, currentGovernment.getGraneries().get(i).getElements(name) + currentGovernment.getGraneries().get(i).getCapacity());
                    amount -= currentGovernment.getGraneries().get(i).getCapacity();
                    currentGovernment.getGraneries().get(i).setCapacity(0.0);
                }
            }
        } else if (armoury.contains(name)) {
            for (int i = 0; i < currentGovernment.getArmouries().size(); i++) {
                if (currentGovernment.getArmouries().get(i).getCapacity() > amount) {
                    currentGovernment.getArmouries().get(i).changeCapacity((double) amount);
                    System.out.println(currentGovernment.getArmouries().get(0).getElements("Bow"));
                    currentGovernment.getArmouries().get(i).setElements(name, currentGovernment.getArmouries().get(i).getElements(name) + amount);
                    System.out.println(currentGovernment.getArmouries().get(0).getElements("Bow"));
                    break;
                } else {
                    currentGovernment.getArmouries().get(i).setElements(name, currentGovernment.getArmouries().get(i).getElements(name) + currentGovernment.getArmouries().get(i).getCapacity());
                    amount -= currentGovernment.getArmouries().get(i).getCapacity();
                    currentGovernment.getArmouries().get(i).setCapacity(0.0);
                }
            }
        } else if (stockpile.contains(name)) {
            for (int i = 0; i < currentGovernment.getStockPiles().size(); i++) {
                if (currentGovernment.getStockPiles().get(i).getCapacity() > amount) {
                    currentGovernment.getStockPiles().get(i).changeCapacity((double) amount);
                    currentGovernment.getStockPiles().get(i).setElements(name, currentGovernment.getStockPiles().get(i).getElements(name) + amount);
                    break;
                } else {
                    currentGovernment.getStockPiles().get(i).setElements(name, currentGovernment.getStockPiles().get(i).getElements(name) + currentGovernment.getStockPiles().get(i).getCapacity());
                    amount -= currentGovernment.getStockPiles().get(i).getCapacity();
                    currentGovernment.getStockPiles().get(i).setCapacity(0.0);
                }
            }

        }
        return "The item purchased";
    }

    public String sell(Matcher matcher) {
        String name = matcher.group("name");
        int amount = (int) Double.parseDouble(matcher.group("amount"));
        String granery = "AppleMeatCheeseBread";
        String stockpile = "stonewoodironpitchwheatflour";
        String armoury = "LeatherArmourMaceSwordMetalArmourBowCrossbowSpearPike";
        Good good = new Good("good", 0.0, 0.0);
        if ((granery.contains(name) && (granerygood(currentGovernment, name)) < amount) ||
                (armoury.contains(name) && (armourygood(currentGovernment, name)) < amount) ||
                (stockpile.contains(name) && (good.getGoodByName(name).getNumber()) < amount)) {

            return "You have not enough good!";
        }
        currentGovernment.setCoin(currentGovernment.getCoin() + (amount * good.getGoodByName(name).getSell()));
        good.getGoodByName(name).setNumber(good.getGoodByName(name).getNumber() - amount);
        if (granery.contains(name)) {
            for (int i = 0; i < currentGovernment.getGraneries().size(); i++) {
                if (currentGovernment.getGraneries().get(i).getElements(name) > amount) {
                    currentGovernment.getGraneries().get(i).changeCapacity((double) -amount);
                    currentGovernment.getGraneries().get(i).setElements(name, currentGovernment.getGraneries().get(i).getElements(name) - amount);
                    break;
                } else {
                    currentGovernment.getGraneries().get(i).changeCapacity(-currentGovernment.getGraneries().get(i).getElements(name));
                    amount -= currentGovernment.getGraneries().get(i).getElements(name);
                    currentGovernment.getGraneries().get(i).setElements(name, 0.0);
                }
            }
        } else if (armoury.contains(name)) {
            for (int i = 0; i < currentGovernment.getArmouries().size(); i++) {
                if (currentGovernment.getArmouries().get(i).getElements(name) > amount) {
                    currentGovernment.getArmouries().get(i).changeCapacity((double) -amount);
                    currentGovernment.getArmouries().get(i).setElements(name, currentGovernment.getArmouries().get(i).getElements(name) - amount);
                    break;
                } else {
                    currentGovernment.getArmouries().get(i).changeCapacity(-currentGovernment.getArmouries().get(i).getElements(name));
                    amount -= currentGovernment.getArmouries().get(i).getElements(name);
                    currentGovernment.getArmouries().get(i).setElements(name, 0.0);
                }
            }
        } else if (stockpile.contains(name))
            for (int i = 0; i < currentGovernment.getStockPiles().size(); i++) {
                if (currentGovernment.getStockPiles().get(i).getElements(name) > amount) {
                    currentGovernment.getStockPiles().get(i).changeCapacity((double) -amount);
                    currentGovernment.getStockPiles().get(i).setElements(name, currentGovernment.getStockPiles().get(i).getElements(name) - amount);
                    break;
                } else {
                    currentGovernment.getStockPiles().get(i).changeCapacity(-currentGovernment.getStockPiles().get(i).getElements(name));
                    amount -= currentGovernment.getStockPiles().get(i).getElements(name);
                    currentGovernment.getStockPiles().get(i).setElements(name, 0.0);
                }
            }
        return "The item was sold";
    }

    public Double graneryCapacity(Government government) {
        Double tmp = 0.0;
        for (Granery granery : government.getGraneries()) {
            tmp += granery.getCapacity();
        }
        return tmp;
    }

    public Double granerygood(Government government, String name) {
        Double tmp = 0.0;
        for (Granery granery : government.getGraneries()) {
            tmp += granery.getElements(name);
        }
        return tmp;
    }

    public Double armouryCapacity(Government government) {
        Double tmp = 0.0;
        for (Armoury armoury : government.getArmouries()) {
            tmp += armoury.getCapacity();
        }
        return tmp;
    }

    public Double armourygood(Government government, String name) {
        Double tmp = 0.0;
        for (Armoury armoury : government.getArmouries()) {
            tmp += armoury.getElements(name);
        }
        return tmp;
    }

    public Double stockpileCapacity(Government government) {
        Double tmp = 0.0;
        int i = 0;
        for (StockPile stockPile : government.getStockPiles()) {
            tmp += stockPile.getCapacity();
        }
        return tmp;
    }

    public String buy2(String name, Double amount) {
        Matcher matcher;
        String command = "buy -i " + name + " -a " + amount;
        matcher = isMatched(command, "^buy(((?: -a (?<amount>\\S+))|(?: -i (?<name>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$");
        return buy(matcher);
    }

    public String sell2(String name, Double amount) {
        Matcher matcher;
        String command = "sell -i " + name + " -a " + amount;
        matcher = isMatched(command, "^sell(((?: -a (?<amount>\\S+))|(?: -i (?<name>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$");
        System.out.println(matcher.group("amount"));
        return sell(matcher);
    }

}
