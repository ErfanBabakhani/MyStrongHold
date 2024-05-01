import java.util.ArrayList;

public class User {
    private ArrayList chats = new ArrayList<Chat>();
    private String id;
    private String name;
    private String password;
    static Messenger messenger = new Messenger();

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public void addGroup(Group group) {
        messenger.addGroup(group);
        this.chats.add(group);
        // System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>" + this.chats.size());
        this.getChats();

    }

    public void addChannel(Channel channel) {
        messenger.addChannel(channel);
        this.chats.add(channel);
        // System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>" + this.chats.size());
        // //./././././
    }

    public void addPrivateChat(PrivateChat pv) {
        this.chats.add(pv);
        // System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>" + this.chats.size());
        // //..//./../

        // ((PrivateChat)chats.get(0)).getId()
    }

    public Group getGroupById(String id) {

        return messenger.getGroupById(id);
    }

    public Channel getChannelById(String id) {

        return messenger.getChannelBuyId(id);
    }

    public PrivateChat getPrivateChatById(String id) {
        PrivateChat returningPrivateChat = new PrivateChat(null, null, null);

        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i) instanceof PrivateChat && ((PrivateChat) chats.get(i)).getId().equals(id)) {
                returningPrivateChat = ((PrivateChat) chats.get(i));
            }
        }
        return returningPrivateChat;
    }

    public String getName() {

        return this.name;
    }

    public String getPassword() {

        return this.password;
    }

    public String getId() {

        return this.id;
    }

    public ArrayList<Chat> getChats() {
        // System.out.println("??????????????????????????????"+this.chats.size());
        return this.chats;
    }

    public void changeChatOrdes(Chat target) { // swap the last chat with thid target
        int lastIndex = this.chats.size() - 1;
        int targetIndex = this.chats.indexOf(target);
        if (lastIndex < 0 || targetIndex < 0) {
            // System.out.println("NONO");
            return;
        }
        Chat temp = ((Chat) this.chats.get(targetIndex));
        for (int i = targetIndex; i < lastIndex; i++) {
            this.chats.set(i, this.chats.get(i + 1));
        }
        this.chats.set(lastIndex, temp);
        return;

    }

}
