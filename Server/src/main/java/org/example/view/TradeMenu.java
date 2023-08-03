package org.example.view;

import org.example.controller.TradeController;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class TradeMenu extends Menu {
    TradeController tradeController;

    public void setTradeController(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public void run(Socket socket) {
        DataBase.writeAMessageToClient(tradeController.showAllPlayers(), socket);
        String command;
        Matcher matcher;
        while (true) {
            command = DataBase.readFromSocket(socket);
            if (command.equals(""))
                continue;
            if ((matcher = isMatched(command, "^trade(((?: -a (?<amount>\\S+))|(?: -p (?<price>\\S+))|(?: -t (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -m (?<message>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,4}$")) != null) {
                if (matcher.group("amount") == null)
                    printMassage("amount is null", socket);
                else if (checkInt(matcher.group("amount")))
                    printMassage("amount must be Integer", socket);
                else if (matcher.group("price") == null)
                    printMassage("price is null", socket);
                else if (checkInt(matcher.group("price")))
                    printMassage("price must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else
                    DataBase.writeAMessageToClient(tradeController.trade(matcher), socket);
            } else if ((matcher = isMatched(command, "^trade list$")) != null)
                DataBase.writeAMessageToClient(tradeController.tradeList(), socket);
            else if ((matcher = isMatched(command, "^trade accept(((?: -i (?<id>\\S+))|(?: -m (?<message>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}")) != null) {
                if (matcher.group("id") == null)
                    printMassage("id is null", socket);
                else if (checkInt(matcher.group("id")))
                    printMassage("id must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(tradeController.tradeAccept(matcher), socket);
            } else if ((matcher = isMatched(command, "^trade history$")) != null)
                DataBase.writeAMessageToClient(tradeController.tradeHistory(), socket);
            else if ((matcher = isMatched(command, "back")) != null) {
                break;
            } else
                DataBase.writeAMessageToClient("invalid command!", socket);
        }
    }

}
