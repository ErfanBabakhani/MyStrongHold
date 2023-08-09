package org.example.model;

import org.example.Main;
import org.example.model.GameInfo.Good;
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
    private final static ArrayList<String> typesOfTree = new ArrayList<>();

    static {
        typesOfTree.add("Palm");
        typesOfTree.add("DesertTree");
        typesOfTree.add("Olive");
        typesOfTree.add("Cherry");
        typesOfTree.add("Coconut");

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