package org.example.view;

import org.example.controller.LoginController;
import org.example.controller.ProfileController;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public void run(Socket socket) {
        String command;
        Matcher matcher;
        while (true) {
            command = DataBase.readFromSocket(socket);
            if (command.equals(""))
                continue;
            if ((matcher = isMatched(command, "^profile change((?: -u (?<username>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null)
                DataBase.writeAMessageToClient(ProfileController.changeUsername(matcher), socket);
            else if ((matcher = isMatched(command, "^profile change((?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null)
                DataBase.writeAMessageToClient(ProfileController.changeNickName(matcher), socket);
            else if ((matcher = isMatched(command, "^profile change password(((?: -n (?<newpassword>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -o (?<oldpassword>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,2}$")) != null)
                DataBase.writeAMessageToClient(ProfileController.changePassword(matcher, socket), socket);
            else if ((matcher = isMatched(command, "^profile change((?: -e (?<email>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null)
                DataBase.writeAMessageToClient(ProfileController.changeEmail(matcher), socket);
            else if ((matcher = isMatched(command, "^profile change((?: -s (?<slogan>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$")) != null)
                DataBase.writeAMessageToClient(ProfileController.changeSlogan(matcher, socket), socket);
            else if ((matcher = isMatched(command, "^Profile remove slogan$")) != null)
                DataBase.writeAMessageToClient(ProfileController.removeSlogan(), socket);
            else if ((matcher = isMatched(command, "^profile display rank$")) != null)
                DataBase.writeAMessageToClient(ProfileController.showRank(), socket);
            else if ((matcher = isMatched(command, "^profile display slogan$")) != null)
                DataBase.writeAMessageToClient(ProfileController.showSlogan(), socket);
            else if ((matcher = isMatched(command, "^start game playernum: ((?<num>\\S+)) turn: ((?<turn>\\S+)) xmap: ((?<x>\\S+)) ymap: ((?<y>\\S+))$")) != null)
                DataBase.writeAMessageToClient(LoginController.startGame(matcher, logedInUser, socket), socket);
            else if ((matcher = isMatched(command, "^profile display$")) != null)
                DataBase.writeAMessageToClient(ProfileController.showAll(), socket);
            else if ((matcher = isMatched(command, "^profile display highscore$")) != null)
                DataBase.writeAMessageToClient(ProfileController.showHighScore(), socket);
            else if ((matcher = isMatched(command, "user logout")) != null) {
                DataBase.writeAMessageToClient(LoginController.logout(), socket);
                break;
            } else if ((matcher = isMatched(command, "show all game")) != null) {
                DataBase.writeAMessageToClient(LoginController.showAllGame(), socket);
            } else if ((matcher = isMatched(command, "^join((?: -id (?<id>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}$")) != null) {
                if (matcher.find() && matcher.group("id") == null) {
                    int id = Integer.parseInt(matcher.group("id"));
                    //TODO
                    // Clear
                    System.out.println("id is " + id);
                    LoginController.joinGame(socket, id);
                } else {
                    LoginController.joinGame(socket);
                }
            } else
                DataBase.writeAMessageToClient("invalid command!", socket);

        }
    }

    public static String changePassword(Socket socket) {
        DataBase.writeAMessageToClient("Please enter your new password again AND if you regretted please enter <regret>", socket);
        return DataBase.readFromSocket(socket);
    }

    public static String getPlayers(String massage, Socket socket) {
//        DataBase.writeAMessageToClient(massage, socket);
        return DataBase.readFromSocket(socket);
    }

    public static void winnerPrint(String string, Socket socket) {
        DataBase.writeAMessageToClient(string, socket);
    }
}
