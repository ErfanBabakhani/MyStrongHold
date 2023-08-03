package org.example.model;

import java.net.Socket;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private Integer highScore=0;
    private Socket mainSocket;

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname=nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getEmail() {
        return email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public Socket getMainSocket() {
        return mainSocket;
    }

    public void setMainSocket(Socket mainSocket) {
        this.mainSocket = mainSocket;
    }
}
