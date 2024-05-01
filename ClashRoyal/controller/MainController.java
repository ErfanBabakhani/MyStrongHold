package controller;

import view.*;
import model.*;
import java.util.ArrayList;

public class MainController {
    DataBase dataBase = DataBase.getInstance();

    public User getUserByUsername(String username) {
        User user = new User(null, null);
        for (int i = 0; i < dataBase.getAllUsers().size(); i++) {
            if (((User) dataBase.getAllUsers().get(i)).getUsername().equals(username)) {
                return ((User) dataBase.getAllUsers().get(i));
            }
        }
        return user;
    }

    public ArrayList sortRankByLevelAndExperience() {
        ArrayList allUsers = new ArrayList<User>();
        allUsers.addAll(dataBase.getAllUsers());
        for (int i = 0; i < allUsers.size() - 1; i++) {
            for (int j = i + 1; j < allUsers.size(); j++) {

                if (((User) allUsers.get(i)).getLevel() < ((User) allUsers.get(j)).getLevel()) {
                    swap(allUsers, i, j);
                } else if (((User) allUsers.get(i)).getLevel() == ((User) allUsers.get(j)).getLevel()) {
                    if (((User) allUsers.get(i)).getExperience() < ((User) allUsers.get(j)).getExperience())
                        swap(allUsers, i, j);
                }
            }
        }
        return allUsers;
    }

    public void swap(ArrayList objects, int first, int target) {
        Object temp = (objects.get(first));
        objects.set(first, objects.get(target));
        objects.set(target, temp);

    }

    public boolean varyficationCartName(String name) {

        if (name.equals("Fireball")) {
            return true;
        } else if (name.equals("Heal")) {
            return true;

        } else if (name.equals("Barbarian")) {
            return true;

        } else if (name.equals("Baby Dragon")) {
            return true;

        } else if (name.equals("Ice Wizard")) {
            return true;

        }
        return false;
    }

    public void fixElementToHead(ArrayList arrayList, Object target) {
        boolean checkFind = false;
        int i;
        for (i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(target)) {
                checkFind = true;
                break;
            }
        }
        if (!checkFind)
            return;

        for (int j = i + 1; j < arrayList.size(); j++) {
            arrayList.set(j - 1, arrayList.get(j));
        }
        arrayList.set(arrayList.size() - 1, target);
        return;
    }

    protected void sortTargetUsersByName(ArrayList targetUsers, int start, int finish) {
        for (int i = start; i < finish; i++) {
            for (int j = i + 1; j < finish + 1; j++) {
                if (((User) targetUsers.get(i)).getUsername()
                        .compareTo(((User) targetUsers.get(j)).getUsername()) > 0) {
                    User temp = ((User) targetUsers.get(i));
                    targetUsers.set(i, targetUsers.get(j));
                    targetUsers.set(j, temp);
                }
            }
        }
    }

    public void sortCartNameLexicographic(ArrayList targetCarts, ArrayList targetCartsOwnerName) {
        for (int i = 0; i < targetCarts.size() - 1; i++) {
            for (int j = i + 1; j < targetCarts.size(); j++) {
                if (((Cart) targetCarts.get(i)).getCartName()
                        .compareTo(((Cart) targetCarts.get(j)).getCartName()) > 0) {
                    this.swap(targetCarts, i, j);
                    this.swap(targetCartsOwnerName, i, j);

                }
            }
        }
    }

    public void sortCartNameLexicographic(ArrayList targetCarts) {
        for (int i = 0; i < targetCarts.size() - 1; i++) {
            for (int j = i + 1; j < targetCarts.size(); j++) {
                if (((Cart) targetCarts.get(i)).getCartName()
                        .compareTo(((Cart) targetCarts.get(j)).getCartName()) > 0) {
                    this.swap(targetCarts, i, j);

                }
            }
        }
    }

    public void sortUsersByName(ArrayList targetUsers) {

        for (int i = 1; i < targetUsers.size(); i++) {

            if (((User) targetUsers.get(i)).getLevel() == ((User) targetUsers.get(i - 1)).getLevel()
                    && ((User) targetUsers.get(i)).getExperience() == ((User) targetUsers.get(i - 1)).getExperience()) {
                int start = i - 1;
                while (i < targetUsers.size()) {
                    if (!(((User) targetUsers.get(i)).getLevel() == ((User) targetUsers.get(i - 1)).getLevel()
                            && ((User) targetUsers.get(i)).getExperience() == ((User) targetUsers.get(i - 1))
                                    .getExperience())) {
                        break;
                    }
                    i++;
                }
                int finish = i - 1;
                sortTargetUsersByName(targetUsers, start, finish);
            }
        }

    }

 

}
