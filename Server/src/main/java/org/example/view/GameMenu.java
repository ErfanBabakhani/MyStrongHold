package org.example.view;

import org.example.controller.CheckController;
import org.example.controller.GameController;
import org.example.model.DataBase;
import org.example.model.GameInfo.Game;
import org.example.model.GameInfo.Government;
import org.example.model.GameInfo.Map;
import org.example.model.User;

import java.net.Socket;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void run(Government government, Socket socket) {
        String command;
        Matcher matcher;
        while (true) {
            while (true) {

                if (CheckController.checkPlayerConnection(gameController.getCurrentGame()))
                    break;
                DataBase.writeAMessageToClient(CheckController.showPlayerStatus(gameController.getCurrentGame()), socket);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            command = DataBase.readFromSocket(socket);
            if (command.equals(""))
                continue;
            if (!gameController.getCurrentGovernment().equals(government)) {
                System.out.println(government.getOwner().getNickname() + " " + gameController.getCurrentGovernment().getOwner().getNickname());
                DataBase.writeAMessageToClient("Please wait until your turn", socket);
                continue;
            }
            if ((matcher = isMatched(command, "^show map((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+)){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    gameController.showMap(matcher, socket);
            } else if ((matcher = isMatched(command, "^show popularity factors$")) != null)
                DataBase.writeAMessageToClient(gameController.showPopularityFactors(), socket);
            else if ((matcher = isMatched(command, "^show popularity$")) != null)
                DataBase.writeAMessageToClient(gameController.showPopularity(), socket);
            else if ((matcher = isMatched(command, "^stop patrol$")) != null)
                DataBase.writeAMessageToClient(gameController.stopPatrolUint(), socket);
            else if ((matcher = isMatched(command, "^change turn$")) != null) {
                String end = gameController.changeTurn(socket);
                if (end.equals(""))
                    break;
                else
                    DataBase.writeAMessageToClient(end, socket);
            } else if ((matcher = isMatched(command, "^show food list$")) != null)
                DataBase.writeAMessageToClient(gameController.showFoodList(), socket);
            else if ((matcher = isMatched(command, "^show food rate$")) != null)
                DataBase.writeAMessageToClient(gameController.showFoodRate(), socket);
            else if ((matcher = isMatched(command, "^food rate((?: -r (?<r>\\S+))){1}$")) != null) {
                if (matcher.group("r") == null)
                    printMassage("rate is null", socket);
                else if (checkInt(matcher.group("r")))
                    printMassage("rate must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.setFoodRate(matcher), socket);
            } else if ((matcher = isMatched(command, "^tax rate((?: -r (?<r>\\S+))){1}$")) != null) {
                if (matcher.group("r") == null)
                    printMassage("rate is null", socket);
                else if (checkInt(matcher.group("r")))
                    printMassage("r must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.setTaxRate(matcher), socket);
            } else if ((matcher = isMatched(command, "^tax rate show$")) != null)
                DataBase.writeAMessageToClient(gameController.showTaxRate(), socket);
            else if ((matcher = isMatched(command, "^open shop menu$")) != null)
                DataBase.writeAMessageToClient(gameController.openShop(socket), socket);
            else if ((matcher = isMatched(command, "^open trade menu$")) != null)
                DataBase.writeAMessageToClient(gameController.openTrade(socket), socket);
            else if ((matcher = isMatched(command, "^dropbuilding(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("please enter type", socket);
                else
                    DataBase.writeAMessageToClient(gameController.dropBuilding(matcher), socket);
            } else if ((matcher = isMatched(command, "^select building(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.selectBuilding(matcher), socket);
            } else if ((matcher = isMatched(command, "^createunit(((?: -t (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -c (?<count>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$")) != null) {
                if (matcher.group("count") == null)
                    printMassage("please enter count", socket);
                else if (checkInt(matcher.group("count")))
                    printMassage("count must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("please enter type", socket);
                else
                    DataBase.writeAMessageToClient(gameController.createUnit(matcher), socket);
            } else if ((matcher = isMatched(command, "^repair$")) != null)
                DataBase.writeAMessageToClient(gameController.repair(), socket);
            else if ((matcher = isMatched(command, "^select unit(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.selectUnit(matcher), socket);
            } else if ((matcher = isMatched(command, "^move unit(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.moveUnit(matcher, "x", "y"), socket);
            } else if ((matcher = isMatched(command, "^patrol unit(((?: -x1 (?<x1>\\S+))|(?: -y1 (?<y1>\\S+))|(?: -x2 (?<x2>\\S+))|(?: -y2 (?<y2>\\S+))){0,1}){0,4}$")) != null) {
                if (matcher.group("x1") == null || matcher.group("y1") == null)
                    printMassage("-x1 or -y1 is null", socket);
                else if (checkInt(matcher.group("x1")) || checkInt(matcher.group("y1")))
                    printMassage("x1 or y1 must be Integer", socket);
                else if (matcher.group("x2") == null || matcher.group("y2") == null)
                    printMassage("-x2 or -y2 is null", socket);
                else if (checkInt(matcher.group("x2")) || checkInt(matcher.group("y2")))
                    printMassage("x2 or y2 must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.patrolUnit(matcher), socket);
            } else if ((matcher = isMatched(command, "^set(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -s (?<state>((?:standing)|(?:defensive)|(?:offensive))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (!(matcher.group("state").equals("standing")
                        || matcher.group("state").equals("defensive")
                        || matcher.group("state").equals("offensive")))
                    printMassage("state should be offensive or standing or defensive", socket);
                else
                    DataBase.writeAMessageToClient(gameController.setTroopsState(matcher), socket);
            } else if ((matcher = isMatched(command, "^attack -e (?:(?<x>\\S+)) (?:(?<y>\\S+))$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.attackToEnemy(matcher), socket);
            } else if ((matcher = isMatched(command, "^attack(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.attack(matcher), socket);
            } else if ((matcher = isMatched(command, "^select engine building(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.selectEngineBuilding(matcher), socket);
            } else if ((matcher = isMatched(command, "^engine attack(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.engineAttack(matcher), socket);
            } else if ((matcher = isMatched(command, "^pour oil(?: -d (?<direction>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\")))){1}$")) != null) {
                if (matcher.group("direction") == null)
                    printMassage("direction is null", socket);
                else if (!(matcher.group("direction").equals("west")
                        || matcher.group("direction").equals("east")
                        || matcher.group("direction").equals("north")
                        || matcher.group("direction").equals("south")))
                    printMassage("direction should be west or east or north or south", socket);
                else
                    DataBase.writeAMessageToClient(gameController.pourOil(matcher), socket);
            } else if ((matcher = isMatched(command, "^dig tunnel(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.digTunnel(matcher), socket);
            } else if ((matcher = isMatched(command, "^dig Ditch(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.digDitch(matcher), socket);
            } else if ((matcher = isMatched(command, "^fill Ditch(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.fillDitch(matcher), socket);
            } else if ((matcher = isMatched(command, "^build((?: -q (?<equipmentname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null) {
                if (matcher.group("equipmentname") == null)
                    printMassage("-q is null", socket);
                else
                    DataBase.writeAMessageToClient(gameController.buildBuilding(matcher), socket);
            } else if ((matcher = isMatched(command, "^disband unit$")) != null)
                DataBase.writeAMessageToClient(gameController.disbandUnit(), socket);
            else if ((matcher = isMatched(command, "^settexture(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else
                    DataBase.writeAMessageToClient(gameController.setTexture(matcher), socket);
            } else if ((matcher = isMatched(command, "^settexture(((?: -x1 (?<x1>\\S+))|(?: -y1 (?<y1>\\S+))|(?: -x2 (?<x2>\\S+))|(?: -y2 (?<y2>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,5}$")) != null) {
                if (matcher.group("x1") == null || matcher.group("y1") == null)
                    printMassage("-x1 or -y1 is null", socket);
                else if (checkInt(matcher.group("x1")) || checkInt(matcher.group("y1")))
                    printMassage("x1 or y1 must be Integer", socket);
                else if (matcher.group("x2") == null || matcher.group("y2") == null)
                    printMassage("-x2 or -y2 is null", socket);
                else if (checkInt(matcher.group("x2")) || checkInt(matcher.group("y2")))
                    printMassage("x2 or y2 must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else
                    DataBase.writeAMessageToClient(gameController.setTextureRectangle(matcher), socket);
            } else if ((matcher = isMatched(command, "^clear(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))){0,1}){0,2}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.clear(matcher), socket);
            } else if ((matcher = isMatched(command, "^droprock(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -d (?<direction>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (!(matcher.group("direction").equals("west")
                        || matcher.group("direction").equals("east")
                        || matcher.group("direction").equals("north")
                        || matcher.group("direction").equals("south")))
                    printMassage("direction should be west or east or north or south", socket);
                else
                    DataBase.writeAMessageToClient(gameController.droprock(matcher), socket);
            } else if ((matcher = isMatched(command, "^droptree(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else
                    DataBase.writeAMessageToClient(gameController.dropTree(matcher), socket);
            } else if ((matcher = isMatched(command, "^dropbuilding(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else
                    DataBase.writeAMessageToClient(gameController.dropBuilding(matcher), socket);
            } else if ((matcher = isMatched(command, "^dropunit(((?: -x (?<x>\\S+))|(?: -y (?<y>\\S+))|(?: -type (?<type>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -c (?<count>\\S+))){0,1}){0,4}$")) != null) {
                if (matcher.group("x") == null || matcher.group("y") == null)
                    printMassage("-x or -y is null", socket);
                else if (checkInt(matcher.group("x")) || checkInt(matcher.group("y")))
                    printMassage("x or y must be Integer", socket);
                else if (matcher.group("type") == null)
                    printMassage("type is null", socket);
                else if (matcher.group("count") == null)
                    printMassage("count is null", socket);
                else if (checkInt(matcher.group("count")))
                    printMassage("count must be Integer", socket);
                else
                    DataBase.writeAMessageToClient(gameController.dropUnit(matcher), socket);
            } else if (isMatched(command, "exit") != null) {
                DataBase.writeAMessageToClient("Game Closed", socket);
                break;
            } else if ((matcher = isMatched(command, "^show government info$")) != null) {
                DataBase.writeAMessageToClient(gameController.showGovernmentInfo(), socket);
            } else
                DataBase.writeAMessageToClient("invalid command!", socket);
        }
    }
}
