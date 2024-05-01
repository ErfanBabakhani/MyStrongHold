package controller;

import java.util.regex.Pattern;

import model.Turn;

import java.util.regex.Matcher;
import view.GameMenu;
import model.*;

public class GameController {
    private GameMenu gameMenu;
    MainController mainController = new MainController();

    public GameController(GameMenu gameMenu) {
        setGameMenu(gameMenu);
    }

    public String gameRunner(Pattern[] patterns, String userInput) {
        gameMenu.mainMenu = false;
        gameMenu.logoutStatus = false;
        boolean checkCommandTruth = false;
        Matcher matcher = patterns[0].matcher(userInput);
        int i;
        for (i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(userInput);
            if (matcher.find()) {
                checkCommandTruth = true;
                break;
            }
        }

        if (!checkCommandTruth) {
            return ("Invalid command!\n");

        } else {
            if (i == 0) {
                return (gameMenu.getMenuName() + "\n");

            } else if (i == 1) {
                return enenmyTowerHitpoint(getCurrentTurn().getTurnUserName());
            } else if (i == 2) {
                int line = varyficationLineName(matcher.group(1));
                if (line == -1) {
                    return ("Incorrect line direction!\n");

                } else {
                    return allCartsinLine(line, matcher.group(1));
                }
            } else if (i == 3) {
                int remainingCart = getCurrentTurn().getRemainingCart();
                return ("You can play " + remainingCart + " cards more!\n");

            } else if (i == 4) {
                int remainingMoves = getCurrentTurn().getRemainingMoves();
                return ("You have " + remainingMoves + " moves left!\n");

            } else if (i == 5) {
                String lineDirection = matcher.group(1);
                int row = Integer.parseInt(matcher.group(2));
                String direction = matcher.group(3);
                int lineType = varyficationLineName(lineDirection);
                int directionType = varyficationDirection(direction);
                int remainingMoves = getCurrentTurn().getRemainingMoves();
                if (lineType == -1) {
                    return ("Incorrect line direction!\n");

                } else if (row < 1 || row > 15) {
                    return ("Invalid row number!\n");

                } else if (directionType == 0) {
                    return ("you can only move troops upward or downward!\n");

                } else if (remainingMoves == 0) {
                    return ("You are out of moves!\n");

                } else if (findTroopInThisHome(getCurrentTurn().getTurnUserName(), lineType,
                        row) == null) {
                    return ("You don't have any troops in this place!\n");

                } else if (row + directionType < 1 || row + directionType > 15) {
                    return ("Invalid move!\n");

                } else {
                    Cart cart = findTroopInThisHome(getCurrentTurn().getTurnUserName(), lineType,
                            row);
                    mainController.fixElementToHead(gameMenu.getMap().getAllCarts(), cart);
                    cart.setRow(row + directionType);
                    getCurrentTurn().decreaseRemainingMoves();
                    return (cart.getCartName() + " moved successfully to row " + cart.getRow() + " in line "
                            + matcher.group(1) + "\n");

                }

            } else if (i == 6) {
                int row = Integer.parseInt(matcher.group(3));
                int line = varyficationLineName(matcher.group(2));
                if (matcher.group(1).equals("Heal") || matcher.group(1).equals("Fireball")) {
                    return ("Invalid troop name!\n");

                }
                if (!mainController.varyficationCartName(matcher.group(1))) {
                    return ("Invalid troop name!\n");

                } else if (!isCartExistInTheBattleDeck(getCurrentTurn().getTurnUserName(),
                        matcher.group(1))) {
                    return ("You don't have " + matcher.group(1) + " card in your battle deck!\n");

                } else if (line == -1) {
                    return ("Incorrect line direction!\n");

                } else if (row < 1 || row > 15) {
                    return ("Invalid row number!\n");

                } else if (getCurrentTurn().getTurnUserName().equals("host")) {
                    if (row > 4) {
                        return ("Deploy your troops near your castles!\n");

                    } else if (getCurrentTurn().getRemainingCart() == 0) {
                        return ("You have deployed a troop or spell this turn!\n");
                    } else {
                        getCurrentTurn().decreaseRemainingCart();
                        setAndAddTroopCartToMap("host", matcher.group(1), line, row);
                        return ("You have deployed " + matcher.group(1) + " successfully!\n");

                    }
                } else if (getCurrentTurn().getTurnUserName().equals("guest")) {
                    if (row < 12) {
                        return ("Deploy your troops near your castles!\n");

                    } else if (getCurrentTurn().getRemainingCart() == 0) {
                        return ("You have deployed a troop or spell this turn!\n");
                    } else {
                        getCurrentTurn().decreaseRemainingCart();
                        setAndAddTroopCartToMap("guest", matcher.group(1), line, row);
                        return ("You have deployed " + matcher.group(1) + " successfully!\n");

                    }
                }
            } else if (i == 7) {

                int line = varyficationLineName(matcher.group(1));
                int row = Integer.parseInt(matcher.group(2));
                if (line == -1) {
                    return ("Incorrect line direction!\n");

                } else if (!isCartExistInTheBattleDeck(getCurrentTurn().getTurnUserName(),
                        "Heal")) {
                    return ("You don't have Heal card in your battle deck!\n");

                } else if (row < 1 || row > 15) {
                    return ("Invalid row number!\n");

                } else if (getCurrentTurn().getRemainingCart() == 0) {
                    return ("You have deployed a troop or spell this turn!\n");

                } else {
                    if (getCurrentTurn().getTurnUserName().equals("host")) {
                        getCurrentTurn().decreaseRemainingCart();
                        setAndAddHealCartToMap("host", "Heal", line, row);

                        return ("You have deployed Heal successfully!\n");

                    } else if (getCurrentTurn().getTurnUserName().equals("guest")) {
                        getCurrentTurn().decreaseRemainingCart();
                        setAndAddHealCartToMap("guest", "Heal", line, row);

                        return ("You have deployed Heal successfully!\n");

                    }

                }
            } else if (i == 8) {
                int line = varyficationLineName(matcher.group(1));
                if (line == -1) {
                    return ("Incorrect line direction!\n");

                } else if (getCurrentTurn().getRemainingCart() == 0) {
                    return ("You have deployed a troop or spell this turn!\n");

                } else {
                    if (isEnemyTowerDestroyed(getCurrentTurn().getTurnUserName(), line)) {
                        return ("This castle is already destroyed!\n");

                    }
                    if (getCurrentTurn().getTurnUserName().equals("host")) {
                        Cart cart = new Cart("Fireball", gameMenu.getCurrentUser());
                        cart.setLine(line);
                        cart.setRow(15);
                        gameMenu.getMap().runFireBall("host", cart);
                        gameMenu.getFight().runAndRemoveFireBall();
                        getCurrentTurn().decreaseRemainingCart();
                        return ("You have deployed Fireball successfully!\n");

                    } else if (getCurrentTurn().getTurnUserName().equals("guest")) {
                        Cart cart = new Cart("Fireball", gameMenu.getGuest());
                        cart.setLine(line);
                        cart.setRow(1);
                        gameMenu.getMap().runFireBall("guest", cart);
                        gameMenu.getFight().runAndRemoveFireBall();
                        getCurrentTurn().decreaseRemainingCart();
                        return ("You have deployed Fireball successfully!\n");

                    }
                }
            } else if (i == 9) {
                String nexPlayer = new String();
                if (getCurrentTurn().getTurnUserName().equals("host")) {
                    nexPlayer += ("Player "
                            + findTheNextPlayer(getCurrentTurn())
                            + " is now playing!\n");
                    resetTurn(getCurrentTurn());
                    return nexPlayer;
                } else if (getCurrentTurn().getTurnUserName().equals("guest")) {
                    gameMenu.getFight().runFight();
                    gameMenu.getFight().decreasHealhitpoints("guest");
                    gameMenu.getFight().decreasHealhitpoints("host");

                    gameMenu.getFight().removeDeadCarts();
                    if (gameMenu.getCuurentTurn() == gameMenu.getTurnMaxNum()) {
                        gameMenu.mainMenu = true;
                        return (setTheWiner(gameMenu.getCurrentUser(), gameMenu.getGuest()));

                    } else if (DetroyedTower("host") == 3) {
                        gameMenu.mainMenu = true;
                        return (setTheWiner(gameMenu.getCurrentUser(), gameMenu.getGuest()));

                    } else if (DetroyedTower("guest") == 3) {
                        gameMenu.mainMenu = true;
                        return (setTheWiner(gameMenu.getCurrentUser(), gameMenu.getGuest()));

                    }
                    nexPlayer += ("Player "
                            + findTheNextPlayer(getCurrentTurn()) + " is now playing!\n");

                    gameMenu.getTurns().add(new Turn());
                    increaseCurrentTurn();
                    return nexPlayer;
                }
            }

        }
        return "";
    }

    private void setGameMenu(GameMenu gameMenu) {
        this.gameMenu = gameMenu;
    }

    private int DetroyedTower(String userRole) {

        int deadNum = 0;
        if (userRole.equals("host")) {
            for (int i = 0; i < gameMenu.getMap().getHostTowers().size(); i++) {
                if (((Tower) gameMenu.getMap().getHostTowers().get(i)).getHitpoint() <= 0) {
                    deadNum++;
                }
            }

        } else if (userRole.equals("guest")) {
            for (int i = 0; i < gameMenu.getMap().getGuestTowers().size(); i++) {
                if (((Tower) gameMenu.getMap().getGuestTowers().get(i)).getHitpoint() <= 0) {
                    deadNum++;
                }
            }

        }

        return deadNum;
    }

    private String enenmyTowerHitpoint(String userRole) {
        String enenmyTowerHitpoints = new String();
        Tower middleTower;
        Tower leftTower;
        Tower rightTower;
        if (userRole.equals("host")) {
            middleTower = ((Tower) gameMenu.getGuest().getTowers().get(1));
            leftTower = ((Tower) gameMenu.getGuest().getTowers().get(0));
            rightTower = ((Tower) gameMenu.getGuest().getTowers().get(2));

        } else {
            middleTower = ((Tower) gameMenu.getCurrentUser().getTowers().get(1));
            leftTower = ((Tower) gameMenu.getCurrentUser().getTowers().get(0));
            rightTower = ((Tower) gameMenu.getCurrentUser().getTowers().get(2));
        }
        enenmyTowerHitpoints += ("middle castle: " + getTowerHitpoint(middleTower) + "\n");
        enenmyTowerHitpoints += ("left castle: " + getTowerHitpoint(leftTower) + "\n");
        enenmyTowerHitpoints += ("right castle: " + getTowerHitpoint(rightTower) + "\n");
        return enenmyTowerHitpoints;

    }

    private int getTowerHitpoint(Tower targetTower) {
        if (targetTower.getHitpoint() <= 0)
            return -1;
        return targetTower.getHitpoint();
    }

    private String allCartsinLine(int line, String lineDirection) {
        String allCarts = new String();
        allCarts += (lineDirection + " line:\n");
        for (int i = 1; i < 16; i++) {
            for (int j = 0; j < gameMenu.getMap().getAllCarts().size(); j++) {
                if (isCartInThisLineAndRow(line, i, ((Cart) gameMenu.getMap().getAllCarts().get(j)))) {
                    
                    allCarts += ("row " + i + ": " + ((Cart) gameMenu.getMap().getAllCarts().get(j)).getCartName()
                            + ": "
                            + ((Cart) gameMenu.getMap().getAllCarts().get(j)).getOwner().getUsername()+"\n");
                }
            }
        }
        return allCarts;
    }

    private boolean isCartInThisLineAndRow(int line, int row, Cart cart) {
        if (cart.getLine() == line && cart.getRow() == row)
            return true;
        return false;
    }

    private int varyficationLineName(String lineName) {
        if (lineName.equals("right")) {
            return 3;
        } else if (lineName.equals("left")) {
            return 1;
        } else if (lineName.equals("middle")) {
            return 2;
        }
        return -1;
    }

    private int varyficationDirection(String direction) {
        if (direction.equals("upward")) {
            return 1;
        } else if (direction.equals("downward")) {
            return -1;
        }
        return 0;
    }

    private void increaseCurrentTurn() {
        gameMenu.increaseCurrentTurn();
    }

    private Cart findTroopInThisHome(String userRole, int line, int row) {
        if (userRole.equals("host")) {
            for (int i = 0; i < gameMenu.getMap().getHostTroopCarts().size(); i++) {
                if (((Cart) gameMenu.getMap().getHostTroopCarts().get(i)).getLine() == line
                        && ((Cart) gameMenu.getMap().getHostTroopCarts().get(i)).getRow() == row) {
                    return ((Cart) gameMenu.getMap().getHostTroopCarts().get(i));
                }
            }
        } else if (userRole.equals("guest")) {
            for (int i = 0; i < gameMenu.getMap().getGuestTroopCarts().size(); i++) {
                if (((Cart) gameMenu.getMap().getGuestTroopCarts().get(i)).getLine() == line
                        && ((Cart) gameMenu.getMap().getGuestTroopCarts().get(i)).getRow() == row) {
                    return ((Cart) gameMenu.getMap().getGuestTroopCarts().get(i));
                }
            }
        }
        return null;
    }

    private boolean isCartExistInTheBattleDeck(String userRole, String cartName) {
        if (userRole.equals("host")) {
            if (gameMenu.getCurrentUser().getBattleDeck().getCartByName(cartName) == null) {
                return false;
            }
        } else if (userRole.equals("guest")) {
            if (gameMenu.getGuest().getBattleDeck().getCartByName(cartName) == null) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void setAndAddTroopCartToMap(String userRole, String cartName, int line, int row) {
        User owner;
        if (userRole.equals("host")) {
            owner = gameMenu.getCurrentUser();
        } else {
            owner = gameMenu.getGuest();
        }
        Cart cart = new Cart(cartName, owner);
        cart.setLine(line);
        cart.setRow(row);
        gameMenu.getMap().addTroopCartToMap(userRole, cart);
        
    }

    private void setAndAddHealCartToMap(String userRole, String cartName, int line, int row) {
        User owner;
        if (userRole.equals("host")) {
            owner = gameMenu.getCurrentUser();
        } else {
            owner = gameMenu.getGuest();
        }
        Cart cart = new Cart(cartName, owner);
        cart.setLine(line);
        cart.setRow(row);
        gameMenu.getMap().runHeal(userRole, cart);
    }

    private boolean isEnemyTowerDestroyed(String userRole, int line) {

        if (userRole.equals("host")) {
            if (((Tower) gameMenu.getMap().getGuestTowers().get(line - 1)).getHitpoint() <= 0)
                return true;
        } else if (userRole.equals("guest")) {
            if (((Tower) gameMenu.getMap().getHostTowers().get(line - 1)).getHitpoint() <= 0)
                return true;
        }
        return false;
    }

    private String setTheWiner(User host, User guest) {

        if (DetroyedTower("guest") == 3 && DetroyedTower("host") != 3) {
            setGolds();
            levelUp(sumOfTowesHitpoint(host), host);
            levelUp(sumOfTowesHitpoint(guest), guest);
            return "Game has ended. Winner: " + host.getUsername() + "\n";
        } else if (DetroyedTower("host") == 3 && DetroyedTower("guest") != 3) {
            setGolds();
            levelUp(sumOfTowesHitpoint(host), host);
            levelUp(sumOfTowesHitpoint(guest), guest);
            return "Game has ended. Winner: " + guest.getUsername() + "\n";
        } else if (DetroyedTower("guest") == 3 && DetroyedTower("host") == 3) {
            setGolds();
            levelUp(sumOfTowesHitpoint(host), host);
            levelUp(sumOfTowesHitpoint(guest), guest);
            return "Game has ended. Result: Tie\n";
        }
        if (DetroyedTower("guest") != 3 && DetroyedTower("host") != 3) {

            if (sumOfTowesHitpoint(guest) > sumOfTowesHitpoint(host)) {
                setGolds();
                levelUp(sumOfTowesHitpoint(host), host);
                levelUp(sumOfTowesHitpoint(guest), guest);
                return "Game has ended. Winner: " + guest.getUsername() + "\n";
            } else if (sumOfTowesHitpoint(guest) < sumOfTowesHitpoint(host)) {
                setGolds();
                levelUp(sumOfTowesHitpoint(host), host);
                levelUp(sumOfTowesHitpoint(guest), guest);
                return "Game has ended. Winner: " + host.getUsername() + "\n";
            } else if (sumOfTowesHitpoint(guest) == sumOfTowesHitpoint(host)) {
                setGolds();
                levelUp(sumOfTowesHitpoint(host), host);
                levelUp(sumOfTowesHitpoint(guest), guest);
                return "Game has ended. Result: Tie\n";
            }
        }

        return null;
    }

    private int sumOfTowesHitpoint(User targetUser) {
        int sumHitpoints = 0;

        if (targetUser.getUsername().equals(gameMenu.getCurrentUser().getUsername())) {
            for (int i = 0; i < targetUser.getTowers().size(); i++) {
                if (((Tower) gameMenu.getMap().getHostTowers().get(i)).getHitpoint() <= 0)
                    continue;
                sumHitpoints += ((Tower) gameMenu.getMap().getHostTowers().get(i)).getHitpoint();
            }
        } else if (targetUser.getUsername().equals(gameMenu.getGuest().getUsername())) {
            for (int i = 0; i < targetUser.getTowers().size(); i++) {
                if (((Tower) gameMenu.getMap().getGuestTowers().get(i)).getHitpoint() <= 0)
                    continue;
                sumHitpoints += ((Tower) gameMenu.getMap().getGuestTowers().get(i)).getHitpoint();
            }
        }

        return sumHitpoints;
    }

    private User getUserByRole(String userRole) {
        if (userRole.equals("guest"))
            return gameMenu.getGuest();
        else if (userRole.equals("host"))
            return gameMenu.getCurrentUser();
        return null;
    }

    private void resetTurn(Turn turn) {
        turn.setRemainingCart(1);
        turn.setRemainingMoves(3);
        turn.setTurnUserName("guest");
    }

    private String findTheNextPlayer(Turn turn) {
        if (turn.getTurnUserName().equals("host")) {
            return gameMenu.getGuest().getUsername();
        } else if (turn.getTurnUserName().equals("guest")) {
            return gameMenu.getCurrentUser().getUsername();
        }
        return "";
    }

    private void levelUp(int remainingHitpoints, User targetUser) {

        if (remainingHitpoints <= 0) {
            targetUser.updateTowersHitpoint();
            targetUser.updateTowersFightStrong();
            return;
        }
        int needExperienceToLevelUp = targetUser.needExperienceToLevelUp();
        remainingHitpoints += targetUser.getExperience();

        while (remainingHitpoints >= needExperienceToLevelUp) {

            remainingHitpoints -= needExperienceToLevelUp;
            targetUser.setLevel(targetUser.getLevel() + 1);
            needExperienceToLevelUp = targetUser.needExperienceToLevelUp();
            if (remainingHitpoints < needExperienceToLevelUp) {
                break;
            }
        }
        int experience = remainingHitpoints;
        targetUser.updateTowersHitpoint();
        targetUser.updateTowersFightStrong();

        targetUser.setExperience(experience);

    }

    private void setGolds() {
        int hostBenefit = DetroyedTower("guest") * 25;
        int guestBenefit = DetroyedTower("host") * 25;

        gameMenu.getGuest().setGold(gameMenu.getGuest().getGold() + guestBenefit);
        gameMenu.getCurrentUser().setGold(gameMenu.getCurrentUser().getGold() + hostBenefit);

    }

    private Turn getCurrentTurn() {
        return ((Turn) gameMenu.getTurns().get(gameMenu.getCuurentTurn() - 1));
    }
}