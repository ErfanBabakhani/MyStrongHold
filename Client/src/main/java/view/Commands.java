package view;

import controller.ConnectionHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Commands {
    public static final Scanner scanner = new Scanner(System.in);

    private static DataOutputStream write;

    public void start() throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.tryToConnect();

        readFromSocket();
        write = new DataOutputStream(ConnectionHandler.getMainSocket().getOutputStream());
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
                    System.out.println("Disconnected from socket");
                    System.exit(1);
                }
            }
        })).start();
    }

    private void startToRead() {
        (new Thread(() -> {
            String command;
            while (true) {
                try {
                    Thread.sleep(1000);
                    command = readFromSocket();
                    if (command.equals("Please enter chat type")) {
                        //TODO
                        new Thread(() -> ChatView.main(new String[1])).start();
                    }
                } catch (Exception e) {

                }
            }
        })).start();
    }

    private void startChatStage() {
        (new Thread(() -> {
            String command;
            while (true) {
                System.out.println("q");
                try {
                    Thread.sleep(1000);
                    command = readFromSocket();
                    if (command.equals(""))
                        continue;
                    else if (command.equals("Please enter chat type")) {
                        //TODO
                        new Thread(() -> ChatView.main(new String[1])).start();
                    } else if (command.equals("1")) {
                        System.out.println("1");
                    }
                } catch (Exception e) {

                }
            }
        })).start();
    }

    private String readFromSocket() {
        String out = "";
        try {
            DataInputStream read = new DataInputStream(ConnectionHandler.getMainSocket().getInputStream());
            if (read.available() == 0) {
                return out;
            } else {
                out = read.readUTF();
            }
        } catch (IOException e) {

        }
        System.out.println(out);
        return out;
    }

}
