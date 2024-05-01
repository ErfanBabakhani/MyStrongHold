package controller;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import view.LoginMenu;
import model.*;

public class LoginController {
    private LoginMenu loginMenu;
    DataBase dataBase = DataBase.getInstance();

    public LoginController(LoginMenu loginMenu) {
        setLoginMenu(loginMenu);
    }

    private void setLoginMenu(LoginMenu loginMenu) {
        this.loginMenu = loginMenu;
    }

    public String loginMenuRunner(Pattern[] patterns, Pattern[] subPatterns, String userInput) {
        loginMenu.mainMenuStatus = false;
        userInput = userInput.replaceAll("\t", " ");
        boolean checkCommandsTruth = false;
        int i;
        Matcher matcher = patterns[0].matcher(userInput);
        for (i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(userInput);
            if (matcher.find()) {
                checkCommandsTruth = true;
                break;
            }
        }
        if (!checkCommandsTruth) {
            return ("invalid command!\n");

        } else {
            if (i == 0) {
                return (loginMenu.getMenuName() + "\n");

            } else if (i == 1) {

                matcher = subPatterns[0].matcher(userInput);
                if (!matcher.find()) {
                    return ("register failed: invalid username format\n");

                } else {
                    if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() != null) {
                        return ("register failed: username already exists\n");

                    }
                    matcher = subPatterns[1].matcher(userInput);
                    if (!matcher.find()) {
                        return ("register failed: invalid password format\n");
                    } else {
                        matcher = subPatterns[2].matcher(userInput);
                        if (!matcher.find()) {
                            return ("register failed: weak password\n");
                        } else {
                            this.register(matcher);
                            return ("register successful\n");
                        }
                    }
                }
            } else if (i == 2) {
                if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() == null) {
                    return ("login failed: username not found\n");
                } else if (!((dataBase.getUsersByUsername(matcher.group(1)).getPassword()).equals(matcher.group(2)))) {
                    return ("login failed: incorrect password\n");
                } else {
                    loginMenu.mainMenuStatus = true;
                    loginMenu.loginedUser = dataBase.getUsersByUsername(matcher.group(1));
                    return ("login successful\n");
                }
            } else if (i == 3) {
                matcher = subPatterns[3].matcher(userInput);
                if (!matcher.find()) {
                    matcher = patterns[3].matcher(userInput);
                    matcher.find();
                    if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() == null) {
                        return ("password change failed: username not found\n");
                    } else if (!(dataBase.getUsersByUsername(matcher.group(1))).getPassword()
                            .equals(matcher.group(2))) {
                        return ("password change failed: incorrect password\n");
                    }
                }
                if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() == null) {
                    return ("password change failed: username not found\n");
                } else if (!(dataBase.getUsersByUsername(matcher.group(1))).getPassword().equals(matcher.group(2))) {
                    return ("password change failed: incorrect password\n");
                }
                matcher = subPatterns[4].matcher(userInput);
                if (!matcher.find()) {
                    return ("password change failed: invalid new password\n");
                }
                matcher = subPatterns[5].matcher(userInput);
                if (!matcher.find()) {
                    return ("password change failed: weak new password\n");
                } else {
                    changePassword(matcher);
                    return ("password change successful\n");
                }
            } else if (i == 4) {

                if (dataBase.getUsersByUsername(matcher.group(1)).getUsername() == null) {
                    return ("remove account failed: username not found\n");
                } else if (!dataBase.getUsersByUsername(matcher.group(1)).getPassword().equals(matcher.group(2))) {
                    return ("remove account failed: incorrect password\n");
                } else {
                    dataBase.removeUser(dataBase.getUsersByUsername(matcher.group(1)));
                    return ("remove account successful\n");
                }
            } else if (i == 5) {
                System.exit(0);
            }
        }

        return "";
    }

    private void register(Matcher matcher) {
        Customer customer = new Customer(matcher.group(1), matcher.group(2));
        this.dataBase.addCustomer(customer);
    }

    public void login(Matcher matcher) {

    }

    public void changePassword(Matcher matcher) {
        this.dataBase.getUsersByUsername(matcher.group(1)).setPassword(matcher.group(3));
    }

}
