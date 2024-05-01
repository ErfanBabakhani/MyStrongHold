import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

// package testp1.Telegeram;

public class Main {
    public static void main(String[] args) {
        // ArrayList comArrayList = new ArrayList<Commands>();
        // comArrayList.add(new Commands(""));

        LoginMenu loginMenuu = new LoginMenu();
        Scanner scanner = new Scanner(System.in);
        Messenger messenger = new Messenger();

        while (true) { // you are in login menu while login
            // System.out.println("!WELCOM TO THE LOGIN MENU!");   //.../././.
            loginMenuu.run(scanner);
            if (loginMenuu.loginStatus) { // if login successfuly
                MessengerMenu messengerMenu = new MessengerMenu(loginMenuu.theLoginedUser);

                while (true) { // you are in messenger menu while you logout or open chat Menu
                    // System.out.println("!WELCOM TO THE MESSENGER MENU!");   //...//.././.
                    messengerMenu.run(scanner);

                    if (messengerMenu.openChatMenu) {
                        ChatMenu chatMenu = new ChatMenu(messengerMenu.getCurrentUser(),messengerMenu.getChat()); // open a chat menu for <id>
                        // chatMenu.setCurrentUser(messengerMenu.getCurrentUser()); //.../././././.
                        while (true) { // you are in chat menu while back to the Messenger Menu
                            // System.out.println("!WELCOM TO THE CHAT MENU!");    //../././.
                            chatMenu.run(scanner, messengerMenu.getChat());
                            if (chatMenu.openMessengerMenu) { // back to the Messenger Menu
                                break;
                            }
                        }
                    }
                    if (messengerMenu.openLoginMenu) { // you logout and go to Login Menu
                        break;
                    }
                }
            }
        }

    }

}
