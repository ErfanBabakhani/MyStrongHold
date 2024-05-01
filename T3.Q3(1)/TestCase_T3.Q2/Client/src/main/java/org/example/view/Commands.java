package org.example.view;

import org.example.controller.ConnectionHandler;

import java.io.*;
import java.util.Scanner;

public class Commands {
    public static final Scanner scanner = new Scanner(System.in);

    private static DataOutputStream write;

    public void start() throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.tryToConnect();

        readFromSocket();
        write = new DataOutputStream(ConnectionHandler.getSocket().getOutputStream());
        startToWrite();
        startToRead();
    }

    private void startToWrite() {
        (new Thread(() -> {
            while (true) {
                String command = scanner.nextLine();
                try {
                    write.writeUTF(command);
                    write.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        })).start();
    }

    private void startToRead() {
        (new Thread(() -> {
            try {
            } catch (Exception e) {
            }
            while (true) {
                try {
                    Thread.sleep(1000);
                    readFromSocket();
                } catch (Exception e) {

                }
            }
        })).start();
    }

    private void readFromSocket() {
        String out = "";
        try {
            DataInputStream read = new DataInputStream(ConnectionHandler.getSocket().getInputStream());
            if (read.available() == 0) {
                return;
            } else {
                out = read.readUTF();
            }
        } catch (IOException e) {

        }
        System.out.println(out);
    }
}
