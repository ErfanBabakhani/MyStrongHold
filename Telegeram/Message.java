public class Message {
    private User owner;
    String content;

    public Message(User owner, String content) {
        this.owner=owner;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public User getOwner() {
        return owner;
    }

}
