package org.example.view;

import org.example.controller.LoginController;
import org.example.controller.SignupController;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class SignupMenu extends Menu {
    public void run(Socket socket) {
        new Thread(() -> {
            String command;
            Matcher matcher;
            while (true) {
                command = DataBase.readFromSocket(socket);
                if(command.equals(""))
                    continue;
                if ((matcher = isMatched(command, "^user create(((?: -u (?<username>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\")))( (?<confirmPassword>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\")))){0,1})|(?<checkSlogan> -s (?<slogan>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -e (?<email>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,5}$")) != null)
                    DataBase.writeAMessageToClient(SignupController.createUser(matcher, socket), socket);
                else if ((matcher = isMatched(command, "^user create(((?: -u (?<username>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>random))|(?: -e (?<email>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,4}$")) != null)
                    DataBase.writeAMessageToClient(SignupController.createUser(matcher, socket), socket);
                else if ((matcher = isMatched(command, "^user create(((?: -u (?<username>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>random))|(?: -s (?<slogan>random))){0,1}){0,4}$")) != null)
                    DataBase.writeAMessageToClient(SignupController.createUser(matcher, socket), socket);
                else if ((matcher = isMatched(command, "^user login(((?: -u (?<username>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?<staylogedin> --stay-logged-in)){0,1}){0,3}$")) != null) {
                    String message = SignupController.login(matcher,socket);
                    DataBase.writeAMessageToClient(message, socket);
                    if (message.equals("user logged in successfully!"))
                        LoginController.start(socket);
                } else if ((matcher = isMatched(command, "^forgot my password((?: -u (?<username>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null) {
                    DataBase.writeAMessageToClient(SignupController.forgetPassword(matcher, socket), socket);
                } else if (isMatched(command, "exit") != null) {
                    DataBase.writeAMessageToClient("Program Has Been Ended", socket);
                    System.exit(0);
                } else
                    DataBase.writeAMessageToClient("Invalid command!", socket);
//                DataBase.writeAMessageToClient(command, socket);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }
        }).start();
    }

    public static Matcher pickSecurityQuestion(String message, Socket socket) {
        Matcher matcher;
        String command;
        DataBase.writeAMessageToClient(message + "Pick your security question: 1. What is my father’s name? 2. What" +
                "was my first pet’s name? 3. What is my mother’s last name?", socket);
        DataBase.writeAMessageToClient("If you regret plz enter <regret> else enter security question number", socket);
        while (true) {
            command = DataBase.readFromSocket(socket);
            if ((matcher = isMatched(command, "^question pick(((?: -q (?<number>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -a (?<answer>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -c (?<answerConfirm>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,3}$")) != null)
                return matcher;
            else if (isMatched(command, "regret") != null) {
                break;
            }
        }
        return null;
    }

    public static String enterAnswerForSet(String question, Socket socket) {
        DataBase.writeAMessageToClient(question, socket);
        DataBase.writeAMessageToClient("Please enter your answer", socket);
        return DataBase.readFromSocket(socket);
    }

    public static void showSlogan(String slogan, Socket socket) {
        DataBase.writeAMessageToClient("Your slogan is\"" + slogan + "\"", socket);
    }

    public static String enterNewPassword(String message, Socket socket) {
        DataBase.writeAMessageToClient(message + "Enter new password", socket);
        return DataBase.readFromSocket(socket);
    }

    public static void reEnterPassword(String randomPassword, Socket socket) {
        String command = "";
        while (!command.equals(randomPassword)) {
            DataBase.writeAMessageToClient("Your random password is: " + randomPassword + "\nPlease re-enter your password here:", socket);
            command = DataBase.readFromSocket(socket);
        }
    }
}