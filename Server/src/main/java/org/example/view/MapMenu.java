package org.example.view;

import org.example.controller.MapController;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class MapMenu extends Menu {
    private MapController mapController;

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public void run(Socket socket) {
        DataBase.writeAMessageToClient(mapController.showMap(0, 0), socket);
        String command;
        Matcher matcher;
        while (true) {
            command = DataBase.readFromSocket(socket);
            if (command.equals(""))
                continue;
            if ((matcher = isMatched(command, "^show map((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+)){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(mapController.showMap(0, 0), socket);
            } else if ((matcher = isMatched(command, "^map(((?: (?<direction>\\S+){1}( (?<x>\\S*)?)?){0,2}))$")) != null) {
                if (matcher.group("direction") == null)
                    printMassage("direction is null", socket);
                else if (checkInt(matcher.group("x")) && matcher.group("x") != null)
                    printMassage("x must be Integer", socket);
                else if (!(matcher.group("direction").equals("west")
                        || matcher.group("direction").equals("east")
                        || matcher.group("direction").equals("north")
                        || matcher.group("direction").equals("south")))
                    printMassage("direction should be west or east or north or south", socket);
                else
                    DataBase.writeAMessageToClient(mapController.moveMap(matcher), socket);
            } else if ((matcher = isMatched(command, "^show details((((?: -x (?<x>\\S+)))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(mapController.showHomeDetails(matcher), socket);
            } else if (isMatched(command, "exit") != null) {
                DataBase.writeAMessageToClient("Exited from map .Entered Game", socket);
                return;
            } else
                DataBase.writeAMessageToClient("invalid command!", socket);

        }
    }
}
