package org.example.view;

import org.example.model.DataBase;
import org.example.model.User;

import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    public static User logedInUser;
    private static final Scanner scanner = new Scanner(System.in);

    public static Matcher isMatched(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }

    protected static Scanner getScanner() {
        return scanner;
    }

    protected static void printMassage(String massage, Socket socket) {
        DataBase.writeAMessageToClient(massage, socket);
    }

    public static void setLogedInUser(User user) {
        logedInUser = user;
    }

    public static boolean checkInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (Exception ex) {
            return true;
        }
        return false;
    }
}
