package view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ChatController {

    public TextField privateChatMember;
    public TextField roomMembers;
    public TextField chatOwner;
    public TextField roomType;
    public TextField mustBeSent;
    public TextField mustBeEditedTo;

    public void publicChat(MouseEvent mouseEvent) {
        Socket chatSocket;
        try {
            chatSocket = new Socket("127.0.0.1", 8000);
            DataBase.writeAMessageToClient("Public", chatSocket);
            DataBase.activeChatSocket = chatSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Course write = DataBase.firstChat();
        DataBase.activeCourse = write;
//        DataBase.addAnewLabel(write, "Salam Erfan\nChetory").setTextFill(Color.DARKRED);
//        DataBase.writeAMessageToClient("Hello every body", chatSocket);
        if (!makeAnewSocketToChat())
            return;
        runChat(write, chatSocket);
    }

    private void runChat(Course write, Socket chatSocket) {
        new Thread(() -> {
            while (true) {

//                try {
//                    Thread.currentThread().wait(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String message = DataBase.readFromSocket(DataBase.activeChatSocket);
//                System.out.println("chat running" + "<<" + message + ">>");

                if (message.equals(""))
                    continue;
                System.out.println(message);
//                if (!makeAnewSocketToChat())
//                    return;

                Matcher matcher = DataBase.editMessage.matcher(message);
                if (matcher.find()) {
                    System.out.println("<<<<EDIT MESSAGE>>>>");
                    tryToEdit(DataBase.findLabelByText(matcher.group("old"), write), matcher.group("name"), write);
                    continue;
                }
                matcher = DataBase.deleteMessage.matcher(message);
                if (matcher.find()) {
                    tryToDelete(DataBase.findLabelByText(matcher.group("name"), write), write);
                    continue;
                }
                System.out.println(message);
                write.getUnHandelMessage().add(message);
//                DataBase.addAnewLabel(write, message);
            }
        }).start();
    }

    public void privateChat(MouseEvent mouseEvent) {
        String pChatMember = privateChatMember.getText();
        System.out.println(pChatMember);
        if (pChatMember == null || pChatMember.equals("")) {
            DataBase.showAlert("please enter private chat member first");
            return;
        }
        Socket chatSocket;
        try {
            chatSocket = new Socket("127.0.0.1", 8000);
            DataBase.writeAMessageToClient("Private", chatSocket);
            DataBase.activeChatSocket = chatSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DataBase.writeAMessageToClient(pChatMember, chatSocket);
        Course write = DataBase.firstChat();
        DataBase.activeCourse = write;
        if (!makeAnewSocketToChat())
            return;
        runChat(write, chatSocket);
    }

    public void room(MouseEvent mouseEvent) {
        //TODO
        String allMembers = roomMembers.getText();
        if (allMembers == null || allMembers.equals("")) {
            DataBase.showAlert("please enter room chat member first (!! split by dash !!)");
            return;
        }
        Socket chatSocket;
        try {
            chatSocket = new Socket("127.0.0.1", 8000);
            DataBase.writeAMessageToClient("Room", chatSocket);
            DataBase.activeChatSocket = chatSocket;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DataBase.writeAMessageToClient(allMembers, chatSocket);
        Course write = DataBase.firstChat();
        DataBase.activeCourse = write;
        if (!makeAnewSocketToChat())
            return;
        runChat(write, chatSocket);
    }

    public void join(MouseEvent mouseEvent) {
        String ownerName = chatOwner.getText();
        String typeOfRoom = roomType.getText();
        if (typeOfRoom == null || typeOfRoom.equals("") || ownerName == null || ownerName.equals("")) {
            DataBase.showAlert("please enter chat type and owner first");
            return;
        }
        Socket chatSocket;
        try {
            chatSocket = new Socket("127.0.0.1", 8000);
            DataBase.activeChatSocket = chatSocket;
            System.out.println("We connected to join Chat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(typeOfRoom);
        System.out.println(ownerName);
        String command = "--joinMember " + ownerName + " -t " + typeOfRoom;
        DataBase.writeAMessageToClient(command, chatSocket);
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        String res = DataBase.readFromSocket(chatSocket);
//        System.out.println("Result --> " + res);
//        if (res.equals("--Stop"))
//            return;
        Course write = DataBase.firstChat();
        DataBase.activeCourse = write;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!makeAnewSocketToChat())
            return;
        runChat(write, chatSocket);
    }

    public void tryToDelete() {
        Label tar = DataBase.selectedMessage;
        if (tar == null) {
            DataBase.showAlert("No selected Label");
            return;
        }
        int lines = DataBase.countLines(tar.getText());
        String text = tar.getText();
        tar.setText("");
        for (int i = 0; i < lines; i++) {
            tar.setText(tar.getText() + "##########\n");
        }
        DataBase.writeAMessageToClient("--delete" + text, DataBase.activeChatSocket);
    }

    public void tryToDelete(Label tar, Course write) {
        if (tar == null) {
            System.out.println("Message Not Found !!");
            return;
        }
//        int lines = DataBase.countLines(tar.getText());
//        tar.setText("");
//        for (int i = 0; i < lines; i++) {
//            tar.setText(tar.getText() + "##########\n");
//        }
        write.getMustBeRemoved().add(tar);
    }

    public void tryToEdit() {
        Label tar = DataBase.selectedMessage;
        if (tar == null) {
            DataBase.showAlert("No selected Label");
            return;
        } else if (this.mustBeEditedTo == null || this.mustBeEditedTo.getText().equals("")) {
            DataBase.showAlert("Please enter replacement text first");
            return;
        }
        String text = tar.getText();
        tar.setText(this.mustBeEditedTo.getText() + "  edited at Time : " + System.currentTimeMillis());
        DataBase.writeAMessageToClient("--edit" + this.mustBeEditedTo.getText() + "--old" + text, DataBase.activeChatSocket);
    }

    public void tryToEdit(Label tar, String mustBeRePlaced, Course write) {
        if (tar == null) {
//            DataBase.showAlert("Message Not Found !!");
            System.out.println(tar);
            return;
        }
//        tar.setText(mustBeRePlaced + "  edited at Time : " + System.currentTimeMillis());
        write.getMustBeRePlacedLabels().add(tar);
        write.getMustBeRePlacedLabelString().add(mustBeRePlaced);
    }

    public void tryToSend() {
        if (mustBeSent == null || mustBeSent.getText().equals("")) {
            DataBase.showAlert("Please enter text first to send");
            return;
        }
//        try {
//            System.out.println("Try connect");
//            Socket socket = new Socket("127.0.0.1", 8000);
//            DataBase.activeChatSocket = socket;
//            System.out.println("We connected");
//        } catch (IOException e) {
//            System.out.println("NOOOOO");
//            return;
//        }
//        if (!makeAnewSocketToChat())
//            return;
        DataBase.addAnewLabel(DataBase.activeCourse, mustBeSent.getText()).setTextFill(Color.DARKBLUE);
        DataBase.addAnewLabel(DataBase.activeCourse, "at Time : " + System.currentTimeMillis()).setTextFill(Color.DARKGOLDENROD);
        System.out.println(mustBeSent.getText());

        DataBase.writeAMessageToClient(mustBeSent.getText(), DataBase.activeChatSocket);
        System.out.println("We send a message");
    }

    public void refresh() {
        ArrayList<String> message = DataBase.activeCourse.getUnHandelMessage();
        for (int i = 0; i < message.size(); i++) {
            DataBase.addAnewLabel(DataBase.activeCourse, message.get(i));
        }
        for (int i = 0; i < DataBase.activeCourse.getMustBeRemoved().size(); i++) {
            DataBase.activeCourse.getMustBeRemoved().get(i).setText("########\n");
        }
        for (int i = 0; i < DataBase.activeCourse.getMustBeRePlacedLabels().size(); i++) {
            DataBase.activeCourse.getMustBeRePlacedLabels().get(i).setText(DataBase.activeCourse.getMustBeRePlacedLabelString().get(i));
        }
//        for (Map.Entry<Label, String> set :
//                DataBase.activeCourse.getMustBeRePlacedLabel().entrySet()) {
//            set.getKey().setText(set.getValue());
//        }
        DataBase.activeCourse.setUnHandelMessage(new ArrayList<>());
        DataBase.activeCourse.setMustBeRemoved(new ArrayList<>());
        DataBase.activeCourse.setMustBeRePlacedLabels(new ArrayList<>());
        DataBase.activeCourse.setMustBeRePlacedLabelString(new ArrayList<>());
    }

    private static boolean makeAnewSocketToChat() {
        try {
            System.out.println("Try connect");
            Socket socket = new Socket("127.0.0.1", 8000);
            DataBase.activeChatSocket = socket;
            System.out.println("We connected");
            return true;
        } catch (IOException e) {
            System.out.println("NOOOOO");
            return false;
        }
    }
}
