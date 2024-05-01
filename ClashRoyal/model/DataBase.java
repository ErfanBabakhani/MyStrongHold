package model;

import java.util.ArrayList;

public class DataBase {
    private static DataBase single_instance = null;

    ArrayList allUsers = new ArrayList<User>();

    private DataBase() {

    }

    public static synchronized DataBase getInstance() {
        if (single_instance == null)
            single_instance = new DataBase();
        return single_instance;
    }

    public ArrayList getAllUsers() {
        return allUsers;
    }

    public void addUser(User user) {
        this.allUsers.add(user);
    }

}
