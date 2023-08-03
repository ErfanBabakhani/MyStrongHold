package view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DataBase {
    public static Label selectedMessage;
    public static Socket activeChatSocket;
    public static Pattern deleteMessage = Pattern.compile("^--delete(?<name>.+)");
    public static Pattern editMessage = Pattern.compile("^--edit(?<name>.+)--old(?<old>.+)");
    public static Course activeCourse;
    public static ChatController chatController = new ChatController();

    public static Course firstChat() {
        Stage stage = new Stage();
        ScrollPane scrollPane = new ScrollPane();
        Pane pane = new Pane();
//        try {
//            pane = FXMLLoader.load(ChatController.class.getResource("/FXML/Chat.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        ImageView imageView = new ImageView(new Image(DataBase.class.getResource("/Image/chat.jpg").toExternalForm()));
        imageView.setFitHeight(1000);
        imageView.setFitWidth(1000);
        pane.getChildren().add(imageView);
        Label label = new Label();
        label.setLayoutY(50);
        label.setTextFill(Color.DARKRED);
        label.setText("WellCome To Chat");
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage1 = new Stage();
                Pane pane1 = null;
                try {
                    pane1 = FXMLLoader.load(ChatController.class.getResource("/FXML/Send.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage1.setScene(new Scene(pane1));
                stage1.show();
            }
        });
        //TODO
//         pane.requestFocus();
//         pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println(event.getCode().getName());
//                if (event.getCode().getName().equals("R")) {
//                    chatController.refresh();
//                    System.out.println("Refresh");
//                }
//            }
//         });
        pane.getChildren().add(label);
        scrollPane.setContent(pane);
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(600);
        stage.setScene(new Scene(scrollPane));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                DataBase.selectedMessage = null;
                DataBase.activeCourse = null;
            }
        });
        return new Course(pane, 50);
    }

    public static Label addAnewLabel(Course course, String message) {
        Label label = new Label(message);
        label.setTextFill(Color.DARKGREEN);
        label.setLayoutY(course.getCurrentY());
        course.getActiveLabels().add(label);
        course.getPane().getChildren().add(label);
        course.setCurrentY(course.getCurrentY() + countLines(message) * 5 + 20);
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Pane pane;
                try {
                    pane = FXMLLoader.load(ChatController.class.getResource("/FXML/Chat.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                selectedMessage = label;
                Stage stage = new Stage();
                stage.setScene(new Scene(pane));
                stage.show();
            }
        });

        return label;
    }

    public static int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    public static void chatMenu(Stage primaryStage) {
        Pane pane;
        try {
            pane = FXMLLoader.load(DataBase.class.getResource("/FXML/ChatMenu.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    public static synchronized void writeAMessageToClient(String message, Socket socket) {
        try {
            DataOutputStream write = new DataOutputStream(socket.getOutputStream());
            write.writeUTF(message);
            write.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromSocket(Socket socket) {
        String out = "";
        try {
            DataInputStream read = new DataInputStream(socket.getInputStream());
            if (read.available() == 0) {
                return out;
            } else {
                out = read.readUTF();
            }
        } catch (IOException e) {

        }
        return out;
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Label findLabelByText(String text, Course write) {
        System.out.println("1");
        if (activeCourse == null)
            return null;
        System.out.println("2");
        Pane tar = activeCourse.getPane();
        ArrayList<Label> allLabel = write.getActiveLabels();
        System.out.println(allLabel.size());
//        for (int i = 0; i < tar.getChildren().size(); i++) {
//            if (!(tar.getChildren() instanceof Label))
//                continue;
//            Label label = ((Label) tar.getChildren().get(i));
//            System.out.println(label.getText() + "VS ME " + text);
//            if (label.getText().contains(text))
//                return label;
//        }
        for (int i = 0; i < allLabel.size(); i++) {
            System.out.println(text + " VS " + allLabel.get(i).getText());
            if (allLabel.get(i).getText().contains(text))
                return allLabel.get(i);
        }
        System.out.println("3");
        return null;
    }
}

