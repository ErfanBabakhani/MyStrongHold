package org.example.controller;

import org.example.view.LoginMenu;
import org.example.model.DataBase;

import java.net.Socket;
import java.util.regex.Matcher;

public class ProfileController extends CheckController {
    public static String changePassword(Matcher matcher, Socket socket) {
        String newPassword = matcher.group("newpassword");
        newPassword = deleteQuotation(newPassword);
        String oldPassword = matcher.group("oldpassword");
        oldPassword = deleteQuotation(oldPassword);
        if (newPassword == null)
            return "New password is empty";
        if (oldPassword == null)
            return "Old password is empty";
        if (!oldPassword.equals(logedInuser.getPassword()))
            return "Current password is incorrect!";
        if (oldPassword.equals(newPassword))
            return "Please enter a new password!";
        boolean randomBoll = !newPassword.equals("random");
        if (newPassword.equals("random"))
            newPassword = SignupController.makeRandomPassword(socket);
        String passwordError = checkPassword(newPassword);
        if (!passwordError.equals("accepted"))
            return passwordError;
        if (randomBoll) {
            String confirm = LoginMenu.changePassword(socket);
            while (!confirm.equals(newPassword)) {
                if (confirm.equals("regret"))
                    return "Changing password failed";
                confirm = LoginMenu.changePassword(socket);
            }
        }
        logedInuser.setPassword(newPassword);
        return "Password changed";
    }

    public static String changeNickName(Matcher matcher) {
        String nickname = matcher.group("nickname");
        nickname = deleteQuotation(nickname);
        if (nickname == null)
            return "New nickname is empty";
        logedInuser.setNickname(nickname);
        return "Nickname changed";
    }

    public static String changeUsername(Matcher matcher) {
        String username = matcher.group("username");
        username = deleteQuotation(username);
        if (isNull(username))
            return "New username is empty";
        if (!checkUsername(username))
            return "New username format is invalid";
        if (DataBase.getUserByName(username) != null)
            return "New username exist";
        logedInuser.setUsername(username);
        return "Username changed";
    }

    public static String changeEmail(Matcher matcher) {
        String email = matcher.group("email");
        email = deleteQuotation(email);
        if (email == null)
            return "New email is empty";
        String Error = checkEmail(email);
        if (!Error.equals("accepted"))
            return Error;
        if (DataBase.getEmails() != null || DataBase.getEmails().size() == 0)
            for (String email1 : DataBase.getEmails()) {
                if (email1.equalsIgnoreCase(email))
                    return "Email is exist";
            }
        if (logedInuser.getEmail() != null)
            DataBase.getEmails().remove(logedInuser.getEmail());
        DataBase.getEmails().add(email);
        logedInuser.setEmail(email);
        return "Email changed";
    }

    public static String changeSlogan(Matcher matcher, Socket socket) {
        String slogan = matcher.group("slogan");
        slogan = deleteQuotation(slogan);
        if (slogan == null)
            return "New password is empty";
        if (slogan.equals("random"))
            slogan = SignupController.makeRandomSlogan(socket);
        logedInuser.setSlogan(slogan);
        return "Slogan changed";
    }

    public static String removeSlogan() {
        logedInuser.setSlogan(null);
        return "Slogan removed";
    }

    public static String showHighScore() {
        return logedInuser.getHighScore().toString();
    }

    public static String showRank() {
        return DataBase.rank(logedInuser).toString();
    }

    public static String showSlogan() {
        return logedInuser.getSlogan();
    }

    public static String showAll() {
        return "Your username:" + logedInuser.getUsername() +
                "\nyour password:" + logedInuser.getPassword() +
                "\nyour nickname:" + logedInuser.getNickname() +
                "\nyour slogan:" + logedInuser.getSlogan() +
                "\nyour email:" + logedInuser.getEmail() +
                "\nyour securityQuestion:" + DataBase.selectSecurityQuestion(logedInuser.getSecurityQuestion()) +
                " and your answer:" + logedInuser.getSecurityQuestionAnswer() +
                "\nyour highscore:" + logedInuser.getHighScore();

    }

}
