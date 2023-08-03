package org.example.controller;

import org.example.Main;
import org.example.model.GameInfo.ChatMember;
import org.example.model.GameInfo.Room;
import org.example.model.User;
import org.example.view.Menu;
import org.example.view.SignupMenu;
import org.example.model.DataBase;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;

public class SignupController extends CheckController {
    private static User stayLoggedIn = null;
    private static Integer invalidLogin = 0;
    private static String invalidUsername = "";

//    public static void start() {
//        SignupMenu signupMenu = new SignupMenu();
//        signupMenu.run();
//    }

    public static String createUser(Matcher matcher, Socket socket) {
        String username = matcher.group("username");
        username = deleteQuotation(username);
        String password = matcher.group("password");
        password = deleteQuotation(password);
        String slogan = matcher.group("slogan");
        slogan = deleteQuotation(slogan);
        String confirmPassword = matcher.group("confirmPassword");
        confirmPassword = deleteQuotation(confirmPassword);
        if (isNull(username))
            return "Username is empty";
        if (isNull(password))
            return "Password is empty";
        if (!matcher.group("password").equals("random") && isNull(confirmPassword))
            return "confirmPassword is empty";
        if (isNull(matcher.group("nickname")))
            return "Nickname is empty";
        if (!checkUsername(username))
            return "Username format is invalid";
        if (DataBase.getUserByName(username) != null) {
            while (true) {
                if (DataBase.getUserByName(username + "_") != null)
                    username = username + ("_");
                else {
                    username = username + ("_");
                    break;
                }
            }
            return "Username is exit BUT you can choose this Username " + username;
        }
        if (matcher.group("checkSlogan") != null && slogan == null)
            return "You doesn't entered any slogan after -s";
        if (matcher.group("checkSlogan") != null && slogan.equals("random"))
            slogan = makeRandomSlogan(socket);
        if (matcher.group("password").equals("random"))
            password = makeRandomPassword(socket);
        String passwordError = checkPassword(password);
        if (!passwordError.equals("accepted"))
            return passwordError;
        if (!matcher.group("password").equals("random") && !password.equals(confirmPassword))
            return "Password confirm isn't correct";
        User user = new User(username, password, matcher.group("nickname"));
        if (matcher.group("email") != null) {
            String userEmail = matcher.group("email");
            userEmail = deleteQuotation(userEmail);
            for (String email : DataBase.getEmails()) {
                if (email.equalsIgnoreCase(userEmail))
                    return "Email is exist";
            }
            String emailError = checkEmail(userEmail);
            if (!emailError.equals("accepted"))
                return emailError;
            user.setEmail(userEmail);
        }
        Matcher matcher1 = SignupMenu.pickSecurityQuestion("", socket);
        String number;
        while (true) {
            if (matcher1 == null)
                return "Creating user regretted";
            else
                number = matcher1.group("number");
            if (!(number.equals("1") || number.equals("2") || number.equals("3")))
                matcher1 = SignupMenu.pickSecurityQuestion("Invalid number for Pick\n", socket);
            else if (matcher1.group("answer").equals(matcher1.group("answerConfirm")))
                break;
            else
                matcher1 = SignupMenu.pickSecurityQuestion("Answer confirm is wrong\n", socket);
        }
        if (slogan != null)
            user.setSlogan(slogan);
        user.setSecurityQuestion(number);
        user.setSecurityQuestionAnswer(matcher1.group("answer"));
        DataBase.addUserToDataBase(user);
        return "User created";
    }

    public static String login(Matcher matcher, Socket socket) {
        String username = matcher.group("username");
        username = deleteQuotation(username);
        String password = matcher.group("password");
        password = deleteQuotation(password);
        if (isNull(username))
            return "Username is empty";
        if (DataBase.getUserByName(username) == null)
            return "User doesn't exist";
        if (password == null)
            if (stayLoggedIn != null && !stayLoggedIn.equals(DataBase.getUserByName(username)))
                return "You aren't stayed logged in";
            else if (!stayLoggedIn.equals(DataBase.getUserByName(username))) return "please enter password";
            else {
                invalidLogin = 0;
                invalidUsername = "";
                Menu.setLogedInUser(DataBase.getUserByName(username));
                logedInuser = DataBase.getUserByName(username);
                return "user logged in successfully!";
            }
        if (matcher.group("password") != null && !DataBase.getUserByName(username).getPassword().equals(password)) {
            if (username.equals(invalidUsername)) {
                skipTime();
                invalidLogin++;
                System.out.println(invalidLogin);
            } else {
                invalidLogin = 1;
                invalidUsername = username;
            }
            return "Username and password didn't match!";
        }
        if (!password.equals(DataBase.getUserByName(username).getPassword()))
            return "password is incorrect";
        if (matcher.group("staylogedin") != null)
            stayLoggedIn = DataBase.getUserByName(username);
        invalidLogin = 0;
        invalidUsername = "";
        Menu.setLogedInUser(DataBase.getUserByName(username));
        logedInuser = DataBase.getUserByName(username);
        DataBase.writeAMessageToClient("Please enter chat type", socket);
        DataBase.getUserByName(username).setMainSocket(socket);
        System.out.println(socket.getPort());
        chatHandle(DataBase.getUserByName(username));
        return "user logged in successfully!";
    }

    private static void chatHandle(User loginUser) {
        new Thread(() -> {
            while (true) {
                System.out.println("!");
                try {
                    System.out.println("Wait to server accept");
                    Socket socketChat = Main.getChatServerSocket().accept();
                    System.out.println("Socket accepted");
                    System.out.println(socketChat.getPort());

                    //TODO
                    while (true) {

                        String command = DataBase.readFromSocket(socketChat);
                        if (command.equals("Public")) {
                            System.out.println("PUBLIC");
                            Room room = new Room("Public", new ChatMember(loginUser, socketChat));
                            room.start();
                            DataBase.getAllRooms().add(room);
                        } else if (command.equals("Private")) {
                            System.out.println("PRIVATE");
                            command = DataBase.readFromSocket(socketChat);
                            System.out.println("Private member" + command);
                            if (DataBase.getUserByName(command) == null) {
                                DataBase.writeAMessageToClient("Please enter true user name", socketChat);
                                continue;
                            }
                            ArrayList<User> validMember = new ArrayList<>();
                            validMember.add(DataBase.getUserByName(command));
                            Room room = new Room("Private", new ChatMember(loginUser, socketChat), validMember);
                            room.start();
                            DataBase.getAllRooms().add(room);
                        } else if (command.equals("Room")) {
                            //TODO
                            System.out.println("ROOM");
                            System.out.println("Room" + command);
                            command = DataBase.readFromSocket(socketChat);
                            ArrayList<User> validMember = new ArrayList<>();
                            getUserSplitByDash(validMember, command);
//                            if (DataBase.getUserByName(command) == null) {
//                                DataBase.writeAMessageToClient("Please enter true user name", socketChat);
//                                continue;
//                            }
//                            validMember.add(DataBase.getUserByName(command));
                            Room room = new Room("Room", new ChatMember(loginUser, socketChat), validMember);
                            room.start();
                            DataBase.getAllRooms().add(room);
                        }
                        Matcher matcher = DataBase.joinMember.matcher(command);
                        if (matcher.find()) {
                            //TODO
                            // join it
                            System.out.println("JOIN");
                            String ownerName = matcher.group("name");
                            String roomType = matcher.group("type");
                            System.out.println(ownerName);
                            System.out.println(roomType);
                            Room tar = Room.findRoom(ownerName, roomType);
                            if (tar == null) {
                                System.out.println("invalid Room");
                                DataBase.writeAMessageToClient("--Stop", socketChat);
                                continue;
                            }
                            if (!roomType.equals("Public") && !checkPermission(tar, loginUser)) {
                                System.out.println("No permission!");
                                DataBase.writeAMessageToClient("--Stop", socketChat);
                                continue;
                            }
                            tar.addNewMember(new ChatMember(loginUser, socketChat));
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private static void getUserSplitByDash(ArrayList<User> validMembers, String input) {
        String[] allUsersName = input.split("-");
        for (int i = 0; i < allUsersName.length; i++) {
            if (DataBase.getUserByName(allUsersName[i]) != null)
                validMembers.add(DataBase.getUserByName(allUsersName[i]));
        }
    }

    private static boolean checkPermission(Room room, User user) {
        ArrayList<User> validMembers = room.getValidMembers();
        try {
            for (int i = 0; i < validMembers.size(); i++) {
                if (validMembers.get(i).equals(user))
                    return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static ChatMember makeAMember(User user, Socket socket) {
        if (user == null || socket == null)
            return null;
        return new ChatMember(user, socket);
    }

    public static String makeRandomSlogan(Socket socket) {
        Random random = new Random();
        int randomInt = random.nextInt(4);
        String slogan = DataBase.getSlogans().get(randomInt);
        SignupMenu.showSlogan(slogan, socket);
        return slogan;
    }

    public static String forgetPassword(Matcher matcher, Socket socket) {
        String username = matcher.group("username");
        username = deleteQuotation(username);
        if (matcher.group("username") == null)
            return "Please enter a available username";
        User user = DataBase.getUserByName(username);
        while (true) {
            if (SignupMenu.enterAnswerForSet(DataBase.selectSecurityQuestion(user.getSecurityQuestion()), socket).equals(user.getSecurityQuestionAnswer()))
                break;
        }
        String passwordError = "";
        String password;
        do {
            password = SignupMenu.enterNewPassword(passwordError, socket);
            if (password.equals("random"))
                password = makeRandomPassword(socket);
            passwordError = checkPassword(password);
        } while (!passwordError.equals("accepted"));
        user.setPassword(password);
        if (invalidUsername.equals(username)) {
            invalidUsername = "";
            invalidLogin = 0;
        }
        return "Forgot password :password changed";
    }

    public static String makeRandomPassword(Socket socket) {
        Random random = new Random();
        double randomNum = random.nextInt(2, 10) * random.nextInt(1, 10) * 1.23;
        String randomPass = "Is" + randomNum + "l@m";
        SignupMenu.reEnterPassword(randomPass, socket);
        return randomPass;
    }

    private static void skipTime() {
        try {
            Thread.sleep(invalidLogin * 5000);
        } catch (InterruptedException e) {
            System.out.println("time not skipped");
        }
    }
}
