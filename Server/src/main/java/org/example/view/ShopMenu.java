package org.example.view;

import org.example.controller.ShopController;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class ShopMenu extends Menu {
    ShopController shopController;

    public void setShopController(ShopController shopController) {
        this.shopController = shopController;
    }

    public void run(Socket socket) {
        DataBase.writeAMessageToClient("shop menu opened", socket);
        String command;
        Matcher matcher;
        while (true) {
            command = DataBase.readFromSocket(socket);

            if (command.equals(""))
                continue;
            if ((matcher = isMatched(command, "^show price list$")) != null)
                System.out.print(shopController.showPriceList());
            else if ((matcher = isMatched(command, "^buy(((?: -a (?<amount>\\S+))|(?: -i (?<name>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$")) != null) {
                if (matcher.group("amount") == null)
                    printMassage("amount is null", socket);
                else if (checkInt(matcher.group("amount")))
                    printMassage("amount must be Integer", socket);
                else if ((matcher.group("name")) == null)
                    printMassage("please enter id of good", socket);
                else
                    DataBase.writeAMessageToClient(shopController.buy(matcher), socket);
            } else if ((matcher = isMatched(command, "^sell(((?: -a (?<amount>\\S+))|(?: -i (?<name>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$")) != null) {
                if (matcher.group("amount") == null)
                    printMassage("amount is null", socket);
                else if (checkInt(matcher.group("amount")))
                    printMassage("amount must be Integer", socket);
                else if ((matcher.group("name")) == null)
                    printMassage("please enter id of good", socket);
                else
                    DataBase.writeAMessageToClient(shopController.sell(matcher), socket);
            } else if ((matcher = isMatched(command, "back")) != null) {
                break;
            } else
                DataBase.writeAMessageToClient("Invalid command!", socket);
        }
    }
}
