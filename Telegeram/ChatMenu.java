import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatMenu {
    private Chat chat;
    private User currentUser;
    public boolean openMessengerMenu;
    static Pattern[] patterns = new Pattern[5];

    static {
        patterns[0] = Pattern.compile("^send a message c (.+)$");
        patterns[1] = Pattern.compile("^add member i ([^\\s]+)$");
        patterns[2] = Pattern.compile("^show all messages$");
        patterns[3] = Pattern.compile("^show all members$");
        patterns[4] = Pattern.compile("^back$");
    }

    public ChatMenu(User currentUser, Chat chat) {
        this.chat = chat;
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner, Chat chat) {
        openMessengerMenu = false;
        String userInputInMessengerMenu = scanner.nextLine();
        boolean checkCommandsTruth = false;
        int i;
        Matcher matcher = patterns[0].matcher(userInputInMessengerMenu);
        for (i = 0; i < patterns.length; i++) {
            matcher = patterns[i].matcher(userInputInMessengerMenu);
            if (matcher.find()) {
                checkCommandsTruth = true;
                break;
            }
        }
        if (!checkCommandsTruth) {
            System.out.println("Invalid command!");
            return;
        } else {
            if (i == 0) {
                if (chat instanceof Channel) {
                    if (!chat.getOwner().getId().equals(currentUser.getId())) {
                        System.out.println("You don't have access to send a message!");
                        return;
                    } else {
                        this.sendMessage(matcher);
                        for (int j = 0; j < this.chat.getMemebers().size(); j++) { // changing chat order for members
                            this.changeTheChatOrder(((User) this.chat.getMemebers().get(j)));
                        }
                        System.out.println("Message has been sent successfully!");
                        return;
                    }
                } else if (chat instanceof Group) {
                    // System.out.println("adefsgrdhtfjy");//../../././.
                    this.sendMessage(matcher);
                    for (int j = 0; j < this.chat.getMemebers().size(); j++) { // changing chat order for members
                        this.changeTheChatOrder(((User) this.chat.getMemebers().get(j)));
                    }
                    System.out.println("Message has been sent successfully!");
                    return;
                } else if (chat instanceof PrivateChat) {
                    Message messege = new Message(currentUser, matcher.group(1));
                    this.sendMessage(matcher);
                    if(!this.currentUser.getId().equals(this.currentUser.messenger.getUserBuyId(chat.getId()).getId())){
                        currentUser.messenger.getUserBuyId(chat.getId()).getPrivateChatById(currentUser.getId())
                        .addMessege(messege);
                    }
                    // for (int j = 0; j < this.chat.getMemebers().size(); j++) { // changing chat order for members
                        this.changeTheChatOrder(this.currentUser);
                    // }
                    User friednUser=this.currentUser.messenger.getUserBuyId(chat.getId());
                    // for (int j = 0; j < friednChat.getMemebers().size(); j++) {
                        this.changeTheChatOrder2(friednUser,friednUser.getPrivateChatById(this.currentUser.getId()));
                    // }
                    System.out.println("Message has been sent successfully!");
                    return;
                }
            } else if (i == 1) {
                if (chat instanceof PrivateChat) {
                    // System.out.println("Invalid ChatType"); // .././././././.
                    System.out.println("Invalid command!");
                    return;
                } else {
                    if (currentUser.messenger.getUserBuyId(matcher.group(1)).getId() == null) {
                        System.out.println("No user with this id exists!");
                        return;
                    } else if (!chat.getOwner().getId().equals(currentUser.getId())) {
                        System.out.println("You don't have access to add a member!");
                    } else if (chat.getMemebers().indexOf(currentUser.messenger.getUserBuyId(matcher.group(1))) != -1) {
                        System.out.println("This user is already in the chat!");
                        return;
                    } else {
                        if (chat instanceof Group) {
                            if (!chat.getOwner().equals(currentUser.messenger.getUserBuyId(matcher.group(1)))) {
                                Message messege = new Message(currentUser,
                                        currentUser.messenger.getUserBuyId(matcher.group(1)).getName()
                                                + " has been added to the group!");
                                this.chat.addMessege(messege);
                                this.addMember(matcher);
                                currentUser.messenger.getUserBuyId(matcher.group(1)).addChat(chat);
                                System.out.println("User has been added successfully!");
                                return;
                            }
                        } else if (chat instanceof Channel) {
                            this.addMember(matcher);
                            currentUser.messenger.getUserBuyId(matcher.group(1)).addChat(chat);
                            System.out.println("User has been added successfully!");
                            return;
                        } else {
                            System.out.println("OppsS");
                        }
                    }
                }
            } else if (i == 2) {
                System.out.println("Messages:");
                for (int j = 0; j < chat.getMesseges().size(); j++) {
                    System.out.println(((Message) chat.getMesseges().get(j)).getOwner().getName() + "("
                            + ((Message) chat.getMesseges().get(j)).getOwner().getId() + "): \""
                            + ((Message) chat.getMesseges().get(j)).content + "\"");
                }
                return;
            } else if (i == 3) {
                if (chat instanceof PrivateChat) {
                    System.out.println("Invalid command!");
                    return;
                } else {
                    System.out.println("Members:");
                    for (int j = 0; j < chat.getMemebers().size(); j++) {
                        if (((User) chat.getMemebers().get(j)).getId().equals(chat.getOwner().getId())) {
                            System.out.println("name: " + ((User) chat.getMemebers().get(j)).getName() + ", id: "
                                    + ((User) chat.getMemebers().get(j)).getId() + " *owner");
                        } else {
                            System.out.println("name: " + ((User) chat.getMemebers().get(j)).getName() + ", id: "
                                    + ((User) chat.getMemebers().get(j)).getId());
                        }
                    }
                }
            } else if (i == 4) {
                openMessengerMenu = true;
                return;
            } else {
                System.out.println("OpppsO");
            }
        }
    }

    private String showMessegas() {
        return "";
    }

    private String showMembers() {
        return "";
    }

    private String addMember(Matcher matcher) {
        this.chat.addMember(currentUser.messenger.getUserBuyId(matcher.group(1)));
        return "";
    }

    private String sendMessage(Matcher matcher) {
        Message messege = new Message(currentUser, matcher.group(1));
        this.chat.addMessege(messege);
        return "";
    }

    public void changeTheChatOrder(User user) {
        user.changeChatOrdes(chat);
        return;
    }

    public void changeTheChatOrder2(User user,Chat chat2) {
        user.changeChatOrdes(chat2);
        return;
    }

}
