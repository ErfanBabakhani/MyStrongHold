import java.util.ArrayList;

public class Messenger {
    private ArrayList channels = new ArrayList<Channel>();
    private ArrayList groups = new ArrayList<Group>();
    private ArrayList users = new ArrayList<User>();

    private User currentUser;

    public void addGroup(Group group) {
        this.groups.add(group);

    }

    public void addChannel(Channel channel) {
        this.channels.add(channel);

    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Group getGroupById(String id) {
        Group returningGroup = new Group(null, null, null);

        for (int i = 0; i < groups.size(); i++) {
            if (((Group) groups.get(i)).getId().equals(id)) {
                returningGroup = (((Group) groups.get(i)));
                break;
            }
        }
        return returningGroup;

    }

    public Channel getChannelBuyId(String id) {
        Channel returningChannel = new Channel(null, null, null);

        for (int i = 0; i < channels.size(); i++) {
            if (((Channel) channels.get(i)).getId().equals(id)) {
                returningChannel = (((Channel) channels.get(i)));
                break;
            }
        }
        return returningChannel;

    }

    public User getUserBuyId(String id) {

        User returningUser = new User(null, null, null);

        for (int i = 0; i < users.size(); i++) {
            if (((User) users.get(i)).getId().equals(id)) {
                returningUser = (((User) users.get(i)));
                break;
            }
        }
        return returningUser;

    }

    public ArrayList getChannels() {
        return channels;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
