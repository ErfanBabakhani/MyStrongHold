package org.example.model.GameInfo;

import org.example.Main;
import org.example.model.DataBase;
import org.example.model.User;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Room extends Thread {
    private ArrayList<ChatMember> allMembers = new ArrayList<>();
    private ArrayList<User> validMembers = new ArrayList<>();
    private ArrayList<String> allMessage = new ArrayList<>();
    private String roomType;
    private ChatMember owner;

    public Room(String roomType, ChatMember owner) {
        this.roomType = roomType;
        this.owner = owner;
        this.allMembers.add(owner);
    }

    public Room(String roomType, ChatMember owner, ArrayList<User> validMembers) {
        this.roomType = roomType;
        this.owner = owner;
        this.allMembers.add(owner);
        this.validMembers = validMembers;
    }

    public void addNewMember(ChatMember chatMember) {
        allMembers.add(chatMember);
        System.out.println(allMembers.size());
        handleNewMessage(allMembers.size() - 1);
    }

    public void setAllMembers(ArrayList<ChatMember> allMembers) {
        this.allMembers = allMembers;
    }

    public void setAllMessage(ArrayList<String> allMessage) {
        this.allMessage = allMessage;
    }

    public ArrayList<ChatMember> getAllMembers() {
        return allMembers;
    }

    public void handleNewMessage(int index) {
        System.out.println("AAAA" + index);
        if (index > allMembers.size() - 1) {
            System.out.println(index);
            return;
        }
        System.out.println("BBBB");
        try {
//            System.out.println("Wait to connect 22222");
//            System.out.println("Old Socket" + allMembers.get(index).getSocket().getPort());
            Socket socket = Main.getChatServerSocket().accept();
            allMembers.get(index).setSocket(socket);
            System.out.println("YES new socket " + socket.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String out = DataBase.readFromSocket(allMembers.get(index).getSocket());
                if (out.equals("")) {
                    continue;
                }
                System.out.println(out);
                if (DataBase.deleteMessage.matcher(out).find()) {
                    sendAMessage(out, allMembers.get(index));
                } else if (DataBase.editMessage.matcher(out).find()) {
                    sendAMessage(out, allMembers.get(index));
                } else {
                    out += "Sender : " + allMembers.get(index).getMember().getNickname() + " Time : " + System.currentTimeMillis();
                    System.out.println(out);
                    System.out.println(allMembers.size());
                    sendAMessage(out, allMembers.get(index));
                }
            }
        }).start();
    }


    private void sendAMessage(String message, ChatMember owner) {
        for (int i = 0; i < allMembers.size(); i++) {
            System.out.println(allMembers.get(i));
            //TODO
            if (allMembers.get(i).equals(owner))
                continue;
            DataBase.writeAMessageToClient(message, allMembers.get(i).getSocket());
        }
    }

    public ChatMember getOwner() {
        return owner;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public void run() {
        handleNewMessage(0);
    }

    public static Room findRoom(String ownerName, String roomType) {
        ArrayList<Room> allRooms = DataBase.getAllRooms();
        for (int i = 0; i < allRooms.size(); i++) {
            if (allRooms.get(i).getOwner().getMember().getNickname().equals(ownerName)) {
                if (allRooms.get(i).getRoomType().equals(roomType))
                    return allRooms.get(i);
            }
        }
        return null;
    }

    public ArrayList<User> getValidMembers() {
        return validMembers;
    }
}
