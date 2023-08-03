package org.example.model;

import org.example.Main;
import org.example.model.GameInfo.Government;
import org.example.model.GameInfo.Room;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DataBase {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<String> emails = new ArrayList<>();
    private static ArrayList<String> slogans = new ArrayList<>();
    private final static ArrayList<String> securityQuestions = new ArrayList<>();
    public static Pattern deleteMessage = Pattern.compile("^--delete.+");
    public static Pattern editMessage = Pattern.compile("^--edit(?<new>.+)--old(?<old>.+)");
    public static Pattern joinMember = Pattern.compile("^--joinMember (?<name>.+) -t (?<type>.+)");
    private static ArrayList<Room> allRooms = new ArrayList<>();
    private static ArrayList<ActiveGame> allActiveGames = new ArrayList<>();


    static {
        securityQuestions.add("What is my father’s name?");
        securityQuestions.add("What was my first pet’s name?");
        securityQuestions.add("What is my mother’s last name");
    }

    public static String selectSecurityQuestion(String number) {
        return securityQuestions.get(Integer.parseInt(number) - 1);
    }

    static {
        slogans.add("I shall have my revenge, in this life or the next.");
        slogans.add("o MAHDI adrekni.");
        slogans.add("It is dutchman.");
        slogans.add("We will walk to Jerusalem with the Muslims together.");
        slogans.add("Hüseynçilər");
    }

    private final static ArrayList<String> typesOfFloor = new ArrayList<>();

    static {
        typesOfFloor.add("FlatGround");
        typesOfFloor.add("GravelGround");
        typesOfFloor.add("Rock");
        typesOfFloor.add("StoneGround");
        typesOfFloor.add("IronGround");
        typesOfFloor.add("Grass");
        typesOfFloor.add("Meadow");
        typesOfFloor.add("FoolMeadow");
        typesOfFloor.add("Floor");
        typesOfFloor.add("Oil");
        typesOfFloor.add("Plain");
        typesOfFloor.add("DeapLessWater");
        typesOfFloor.add("River");
        typesOfFloor.add("SmallPond");
        typesOfFloor.add("BigPond");
        typesOfFloor.add("Beach");
        typesOfFloor.add("Sea");
    }

    private final static ArrayList<String> typesOfTree = new ArrayList<>();

    static {
        typesOfTree.add("Palm");
        typesOfTree.add("DesertTree");
        typesOfTree.add("Olive");
        typesOfTree.add("Cherry");
        typesOfTree.add("Coconut");

    }

    private static ArrayList<String> goods = new ArrayList<>();

    static {
        goods.add("Apple");
        goods.add("Meat");
        goods.add("Cheese");
        goods.add("Bread");
        goods.add("stone");
        goods.add("wood");
        goods.add("iron");
        goods.add("pitch");
        goods.add("wheat");
        goods.add("flour");
        goods.add("LeatherArmour");
        goods.add("Mace");
        goods.add("Sword");
        goods.add("MetalArmour");
        goods.add("Bow");
        goods.add("Crossbow");
        goods.add("Spear");
        goods.add("Pike");
    }

    public static ArrayList<String> getGoods() {
        return goods;
    }

    private final static ArrayList<String> typesOfTroops = new ArrayList<>();

    static {
        typesOfTroops.add("Archer");
        typesOfTroops.add("CrossbowMen");
        typesOfTroops.add("SpearMen");
        typesOfTroops.add("PikeMen");
        typesOfTroops.add("MaceMen");
        typesOfTroops.add("SwordsMen");
        typesOfTroops.add("Knight");
        typesOfTroops.add("Tunneler");
        typesOfTroops.add("LadderMen");
        typesOfTroops.add("Engineer");
        typesOfTroops.add("BlackMonk");
        typesOfTroops.add("ArabianBow");
        typesOfTroops.add("Slaves");
        typesOfTroops.add("Slingers");
        typesOfTroops.add("Assassins");
        typesOfTroops.add("HorseArchers");
        typesOfTroops.add("ArabianSwordsMen");
        typesOfTroops.add("FireThrowers");
    }

    private final static ArrayList<String> typesOfBarrack = new ArrayList<>();

    static {
        typesOfBarrack.add("Archer");
        typesOfBarrack.add("CrossbowMen");
        typesOfBarrack.add("SpearMen");
        typesOfBarrack.add("PikeMen");
        typesOfBarrack.add("MaceMen");
        typesOfBarrack.add("SwordsMen");
        typesOfBarrack.add("Knight");
        typesOfBarrack.add("Tunneler");
        typesOfBarrack.add("LadderMen");
        typesOfBarrack.add("BlackMonk");
    }

    private final static ArrayList<String> typesOfEngineerGuild = new ArrayList<>();

    static {
        typesOfEngineerGuild.add("Engineer");
    }

    private final static ArrayList<String> typesOfMercenaryPost = new ArrayList<>();

    static {
        typesOfMercenaryPost.add("ArabianBow");
        typesOfMercenaryPost.add("Slaves");
        typesOfMercenaryPost.add("Slingers");
        typesOfMercenaryPost.add("Assassins");
        typesOfMercenaryPost.add("HorseArchers");
        typesOfMercenaryPost.add("ArabianSwordsMen");
        typesOfMercenaryPost.add("FireThrowers");
    }

    private final static ArrayList<String> typeOfMines = new ArrayList<>();

    static {
        typeOfMines.add("IronMine");
        typeOfMines.add("StoneMine");
        typeOfMines.add("WoodCutter");
        typeOfMines.add("OxTether");
        typeOfMines.add("StockPile");
        typeOfMines.add("armourer");
        typeOfMines.add("blacksmith");
        typeOfMines.add("Fletcher");
        typeOfMines.add("Poleturner");
    }

    private final static ArrayList<String> typeOfWarBuilding = new ArrayList<>();

    static {
        typeOfWarBuilding.add("SmallStoneGatehouse");
        typeOfWarBuilding.add("BigStoneGatehouse");
        typeOfWarBuilding.add("Drawbridge");
        typeOfWarBuilding.add("LookoutTower");
        typeOfWarBuilding.add("PerimeterTower");
        typeOfWarBuilding.add("DefensiveTower");
        typeOfWarBuilding.add("SquareTower");
        typeOfWarBuilding.add("CircleTower");
        typeOfWarBuilding.add("Armoury");
        typeOfWarBuilding.add("Barrack");
        typeOfWarBuilding.add("MercenaryPost");
        typeOfWarBuilding.add("EngineerGuild");
        typeOfWarBuilding.add("KillingPit");
        typeOfWarBuilding.add("OilSmelter");
        typeOfWarBuilding.add("PitchDitch");
        typeOfWarBuilding.add("CagedWarDogs");
        typeOfWarBuilding.add("SiegeTent");
        typeOfWarBuilding.add("Stable");

    }

    private final static ArrayList<String> typeOfFoodFarm = new ArrayList<>();

    static {
        typeOfFoodFarm.add("Mill");
        typeOfFoodFarm.add("Baker");
        typeOfFoodFarm.add("Brewery"); //AbeJo Sazi
        typeOfFoodFarm.add("Granery");
        typeOfFoodFarm.add("AppleGarden");
        typeOfFoodFarm.add("DairyProducts");
        typeOfFoodFarm.add("BarleyField");
        typeOfFoodFarm.add("Hunt");
        typeOfFoodFarm.add("WheatField");
    }

    private final static ArrayList<String> typeOfTownBuilding = new ArrayList<>();

    static {
        typeOfTownBuilding.add("Inn");
        typeOfTownBuilding.add("Hovel");
        typeOfTownBuilding.add("Church");
        typeOfTownBuilding.add("Cathedral");
    }

    public static void addUserToDataBase(User user) {
        users.add(user);
        if (user.getEmail() != null)
            emails.add(user.getEmail());
    }

    public static User getUserByName(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) return users.get(i);
        }
        return null;
    }

    public static Integer rank(User user) {
        Integer rank = 1;
        for (User value : users) {
            if (user.getHighScore() < value.getHighScore()) rank++;
            else if (user.hashCode() < value.hashCode()) rank++;
        }
        return rank;
    }

    public static ArrayList<String> getEmails() {
        return emails;
    }

    public static ArrayList<String> getSlogans() {
        return slogans;
    }

    public static ArrayList<String> getTypesOfTroops() {
        return typesOfTroops;
    }

    public static ArrayList<String> getTypeOfFoodFarm() {
        return typeOfFoodFarm;
    }

    public static ArrayList<String> getTypeOfMines() {
        return typeOfMines;
    }

    public static ArrayList<String> getTypeOfTownBuilding() {
        return typeOfTownBuilding;
    }

    public static ArrayList<String> getTypeOfWarBuilding() {
        return typeOfWarBuilding;
    }

    public static ArrayList<String> getTypesOfFloor() {
        return typesOfFloor;
    }

    public static ArrayList<String> getTypesOfTree() {
        return typesOfTree;
    }

    public static ArrayList<String> getTypesOfBarrack() {
        return typesOfBarrack;
    }

    public static ArrayList<String> getTypesOfEngineerGuild() {
        return typesOfEngineerGuild;
    }

    public static ArrayList<String> getTypesOfMercenaryPost() {
        return typesOfMercenaryPost;
    }

    public static synchronized void writeAMessageToClient(String message, Socket socket) {
        try {
            DataOutputStream write = new DataOutputStream(socket.getOutputStream());
            write.writeUTF(message);
            write.flush();
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }

    public static String readFromSocket(Socket socket) {
        String out = "";
        try {
            DataInputStream read = new DataInputStream(socket.getInputStream());
            if (read.available() == 0) {
                return out;
            } else {
                out = read.readUTF();
            }
        } catch (IOException e) {

        }
        return out;
    }

    public static ArrayList<Room> getAllRooms() {
        return allRooms;
    }

    public static void setAllRooms(ArrayList<Room> allRooms) {
        DataBase.allRooms = allRooms;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static Government findGovernmentByUser(ArrayList<Government> all, User tar) {
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getOwner().equals(tar))
                return all.get(i);
        }
        return null;
    }

    public static ArrayList<ActiveGame> getAllActiveGames() {
        return allActiveGames;
    }
}