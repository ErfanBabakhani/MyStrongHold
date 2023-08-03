package controller;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler {
    private static Socket mainSocket;

    public void tryToConnect() {
        try {
            mainSocket = new Socket("127.0.0.1", 8080);
        } catch (IOException e) {
            System.out.println("Can not connect to the ip : /127.0.0.1 and port : 8080");
            System.exit(1);
        }
    }

    public static Socket getMainSocket() {
        return mainSocket;
    }

}
