package org.example.model.GameInfo;

import org.example.model.User;

import java.net.Socket;

public class ChatMember {
    private User member;
    private Socket socket;

    public ChatMember(User member, Socket socket) {
        this.member = member;
        this.socket = socket;
    }

    public User getMember() {
        return member;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
