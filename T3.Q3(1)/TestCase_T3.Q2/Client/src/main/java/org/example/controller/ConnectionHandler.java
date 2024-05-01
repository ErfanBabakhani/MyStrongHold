package org.example.controller;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler {
    private static Socket socket;

    public void tryToConnect() {
        try {
            socket = new Socket("127.0.0.1", 8080);
        } catch (IOException e) {
            System.out.println("Can not connect to the ip : /127.0.0.1 and port : 8080");
            System.exit(1);
        }
    }

    public static Socket getSocket() {
        return socket;
    }

}
