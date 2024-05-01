import java.util.ArrayList;

public class Chat {
    private ArrayList memebers = new ArrayList<User>();
    private ArrayList messeges = new ArrayList<Message>();
    private User owner;
    private String id;
    private String name;

    public Chat(User owner, String id, String name) {
        this.owner=owner;
        this.id=id;
        this.name=name;
    }

    public void addMember(User user) {
        this.memebers.add(user);
    }

    public void addMessege(Message messege) {
        this.messeges.add(messege);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList getMesseges() {
        return this.messeges;
    }

    public ArrayList getMemebers() {
        return this.memebers;
    }

    public String getId() {
        return this.id;
    }

    public User getOwner() {
        return this.owner;
    }

}
