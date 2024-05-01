import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessengerMenu {
    private Chat chat;
    private User currentUser;
    public boolean openChatMenu;
    public boolean openLoginMenu;
    static Pattern[] patterns = new Pattern[8];
    static Pattern[] subPatterns = new Pattern[2];

    static {
        patterns[0] = Pattern.compile("^show all channels$"); // for identify commands
        patterns[1] = Pattern.compile("^create new channel i [^\\s]+ n [^\\s]+$"); // for identify commands
        subPatterns[0] = Pattern.compile("^create new channel i ([^\\s]+) n ([A-Za-z0-9_]+)$"); // for valfy UserName
        patterns[2] = Pattern.compile("^join channel i ([^\\s]+)$"); // for identify commands
        patterns[3] = Pattern.compile("^show my chats$");
        patterns[4] = Pattern.compile("^create new group i [^\\s]+ n [^\\s]+$"); // for identify commands
        subPatterns[1] = Pattern.compile("^create new group i ([^\\s]+) n ([A-Za-z0-9_]+)$"); // for valfy UserName
        patterns[5] = Pattern.compile("^start a new private chat with i ([^\\s]+)$");
        patterns[6] = Pattern.compile("^logout$");
        patterns[7] = Pattern.compile("^enter (.+) i ([^\\s]+)$");
    }

    // static
    public MessengerMenu(User user) {
        setUser(user);
    }

    public void run(Scanner scanner) {
        openChatMenu = false;
        openLoginMenu = false;
        String userInputInMessengerMenu = scanner.nextLine();

        Matcher matcher = patterns[0].matcher(userInputInMessengerMenu);

        boolean checkCommandsTruth = false;
        int i;
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
                this.showAllChannels();
            } else if (i == 1) {
                matcher = subPatterns[0].matcher(userInputInMessengerMenu);
                if (matcher.find()) {
                    // System.out.println(matcher.group(1)+matcher.group(2)+">>::::"); //.././//...
                    if (currentUser.messenger.getChannelBuyId(matcher.group(1)).getId() != null) {
                        System.out.println("A channel with this id already exists!");
                        return;
                    } else {
                        this.createChannel(matcher);
                        this.joinChannel(matcher);
                        System.out.println("Channel " + matcher.group(2) + " has been created successfully!");
                        return;
                    }
                } else {
                    System.out.println("Channel name's format is invalid!");
                    return;
                }
            } else if (i == 2) {
                if (currentUser.getChannelById(matcher.group(1)).getId() == null) {
                    System.out.println("No channel with this id exists!");
                    return;
                } else if (currentUser.getChannelById(matcher.group(1)).getMemebers().indexOf(currentUser) != -1) {
                    System.out.println("You're already a member of this channel!");
                    return;
                } else {
                    this.joinChannel(matcher);
                    this.currentUser.addChat(currentUser.getChannelById(matcher.group(1)));
                    System.out.println("You have successfully joined the channel!");
                }

            } else if (i == 3) {
                this.showChats();
            }

            else if (i == 4) {
                matcher = subPatterns[1].matcher(userInputInMessengerMenu);
                if (matcher.find()) {
                    // System.out.println(matcher.group(1)+matcher.group(2)+">>::::"); //.././//...
                    if (currentUser.messenger.getGroupById(matcher.group(1)).getId() != null) {
                        System.out.println("A group with this id already exists!");
                        return;
                    } else { // ????
                        this.createGourp(matcher);
                        currentUser.getGroupById(matcher.group(1)).addMember(currentUser);
                        System.out.println("Group " + matcher.group(2) + " has been created successfully!");
                        return;
                    }
                } else {
                    System.out.println("Group name's format is invalid!");
                    return;
                }
            } else if (i == 5) {
                // int indexOfPrivateChatinUsersChats =
                // currentUser.getChats().indexOf(matcher.group(1));
                if (currentUser.messenger.getUserBuyId(matcher.group(1)).getId() == null) {
                    System.out.println("No user with this id exists!");
                    return;
                } else if (this.currentUser.getPrivateChatById(matcher.group(1)).getId() != null) {
                    System.out.println("You already have a private chat with this user!");
                    return;
                } else {
                    createPrivateChat(matcher);
                    // addMemberForPrivateChat(matcher);
                    System.out.println(
                            "Private chat with " + currentUser.messenger.getUserBuyId(matcher.group(1)).getName()
                                    + " has been started successfully!");
                    return;
                }

            } else if (i == 6) {
                System.out.println("Logged out");
                openLoginMenu = true;
                return;
            } else if (i == 7) {

                if (matcher.group(1).equals("private chat")) {
                    if (currentUser.getPrivateChatById(matcher.group(2)).getId() == null) {
                        System.out.println("You have no private chat with this id!");
                        return;
                    } else {
                        openChatMenu = true;
                        chat = currentUser.getPrivateChatById(matcher.group(2));
                        System.out.println("You have successfully entered the chat!");
                        return;
                    }

                } else if (matcher.group(1).equals("channel")) {
                    if (currentUser.getChannelById(matcher.group(2)).getId() == null) {
                        System.out.println("You have no channel with this id!");
                        return;
                    } else {
                        openChatMenu = true;
                        chat = currentUser.getChannelById(matcher.group(2));
                        System.out.println("You have successfully entered the chat!");
                        return;
                    }

                } else if (matcher.group(1).equals("group")) {
                    if (currentUser.getGroupById(matcher.group(2)).getId() == null) {
                        System.out.println("You have no group with this id!");
                        return;
                    } else {
                        openChatMenu = true;
                        chat = currentUser.getGroupById(matcher.group(2));
                        System.out.println("You have successfully entered the chat!");
                        return;
                    }

                } else {
                    System.out.println("Invalid command!");
                    return;
                }
            }

        }

    }

    private String showAllChannels() {
        ArrayList allChannels = new ArrayList<Channel>();
        currentUser.messenger.getChannels();
        allChannels.addAll(currentUser.messenger.getChannels());

        System.out.println("All channels:");
        String string = new String();
        // for (int i = 0; i < currentUser.messenger.getChannels().size(); i++) {
        // System.out.println((i + 1) + ". " + ((Channel)
        // currentUser.messenger.getChannels().get(i)).getName()
        // + ", id: " + ((Channel) currentUser.messenger.getChannels().get(i)).getId() +
        // ", members: "
        // + ((Channel)
        // currentUser.messenger.getChannels().get(i)).getMemebers().size());

        // string += (i + 1) + ". " + ((Channel)
        // currentUser.messenger.getChannels().get(i)).getName()
        // + ", id: " + ((Channel) currentUser.messenger.getChannels().get(i)).getId() +
        // ", members: "
        // + ((Channel)
        // currentUser.messenger.getChannels().get(i)).getMemebers().size();

        // }

        for (int i = 0; i < allChannels.size(); i++) {
            System.out.println((i + 1) + ". " + ((Channel) allChannels.get(i)).getName()
                    + ", id: " + ((Channel) allChannels.get(i)).getId() + ", members: "
                    + ((Channel) allChannels.get(i)).getMemebers().size());

            string += (i + 1) + ". " + ((Channel) allChannels.get(i)).getName()
                    + ", id: " + ((Channel) allChannels.get(i)).getId() + ", members: "
                    + ((Channel) allChannels.get(i)).getMemebers().size();

        }

        return string;
    }

    // private String showChats() {
    // ArrayList allChats = new ArrayList<Chat>();
    // allChats.addAll(currentUser.getChats());
    // String typeOfChat = new String();

    // System.out.println("Chats:");
    // Chat replacementChat = new Chat(null, null, null); // for replace in allChat
    // to not show again first group of
    // // chats(the chat wich we send a message in it)
    // int counter = 0;
    // for (int i = currentUser.getChats().size() - 1; i >= 0; i--) {
    // // System.out.println(((Chat) allChats.get(i)).getId());
    // if (isAnySendenMessageBuyThisUser((Chat) allChats.get(i))) {

    // if (allChats.get(i) instanceof Channel) {
    // typeOfChat = "channel";
    // } else if (allChats.get(i) instanceof Group) {
    // typeOfChat = "group";
    // } else if (allChats.get(i) instanceof PrivateChat) {
    // typeOfChat = "private chat";
    // }

    // System.out.println((counter + 1) + ". " + ((Chat) allChats.get(i)).getName()
    // + ", " + "id: "
    // + ((Chat) allChats.get(i)).getId() + ", " + typeOfChat); // REPAIR IT
    // allChats.set(i, replacementChat);
    // counter++;
    // }
    // }
    // for (int i = allChats.size() - 1; i >= 0; i--) {

    // if (((Chat) allChats.get(i)).getId() != null) {

    // if (allChats.get(i) instanceof Channel) {
    // typeOfChat = "channel";
    // } else if (allChats.get(i) instanceof Group) {
    // typeOfChat = "group";
    // } else if (allChats.get(i) instanceof PrivateChat) {
    // typeOfChat = "private chat";
    // }

    // System.out.println((counter + 1) + ". " + ((Chat) allChats.get(i)).getName()
    // + ", " + "id: "
    // + ((Chat) allChats.get(i)).getId() + ", " + typeOfChat); // REPAIR IT
    // counter++;
    // }
    // }
    // return "";
    // }
    private String showChats() {
        ArrayList allChats = new ArrayList<Chat>();
        allChats.addAll(currentUser.getChats());
        String typeOfChat = new String();

        System.out.println("Chats:");
        int counter = 0;
        for (int i = allChats.size() - 1; i >= 0; i--) {

            if (allChats.get(i) instanceof Channel) {
                typeOfChat = "channel";
            } else if (allChats.get(i) instanceof Group) {
                typeOfChat = "group";
            } else if (allChats.get(i) instanceof PrivateChat) {
                typeOfChat = "private chat";
            }

            System.out.println((counter + 1) + ". " + ((Chat) allChats.get(i)).getName() + ", " + "id: "
                    + ((Chat) allChats.get(i)).getId() + ", " + typeOfChat); // REPAIR IT
            counter++;

        }
        return "";
    }

    private String enterChat(Matcher matcher) {

        return "";
    }

    private String createChannel(Matcher matcher) {

        Channel channel = new Channel(currentUser, matcher.group(1), matcher.group(2));
        // System.out.println(matcher.group(1)+matcher.group(2)); //..//..//
        currentUser.addChannel(channel);

        return "";
    }

    private String createGourp(Matcher matcher) {
        Group group = new Group(currentUser, matcher.group(1), matcher.group(2));
        currentUser.addGroup(group);
        return "";
    }

    private String createPrivateChat(Matcher matcher) {
        PrivateChat privateChat = new PrivateChat(currentUser, matcher.group(1),
                currentUser.messenger.getUserBuyId(matcher.group(1)).getName());
        currentUser.addPrivateChat(privateChat);
        if (!currentUser.getId().equals(currentUser.messenger.getUserBuyId(matcher.group(1)).getId())) {
            PrivateChat privateChat2 = new PrivateChat(currentUser.messenger.getUserBuyId(matcher.group(1)),
                    currentUser.getId(), currentUser.getName());
            currentUser.messenger.getUserBuyId(matcher.group(1)).addPrivateChat(privateChat2);
        }
        return "";
    }

    private String joinChannel(Matcher matcher) {
        if (matcher.group(1) == null) {
            System.out.println("Opps");
        }
        this.currentUser.getChannelById(matcher.group(1)).addMember(currentUser);
        return "";
    }

    private void setUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Chat getChat() {
        return chat;
    }

    // public boolean isAnySendenMessageBuyThisUser(Chat chat) {
    //     for (int i = chat.getMesseges().size() - 1; i >= 0; i--) {
    //         if (((Message) chat.getMesseges().get(i)).getOwner().getId().equals(currentUser.getId())) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // private void addMemberForPrivateChat(Matcher matcher) { //this add we ti our frind private chat member and BARAKS
    //     User theTargetUser=this.currentUser.messenger.getUserBuyId(matcher.group(1));
    //     this.currentUser.getPrivateChatById(matcher.group(1)).addMember(theTargetUser);
    //     theTargetUser.getPrivateChatById(currentUser.getId()).addMember(currentUser);
    // }

}
