package model;
import java.util.ArrayList;


public class DataBase {
    private static DataBase single_instance = null;


    ArrayList allUsers = new ArrayList<User>();
    ArrayList discountCodes = new ArrayList<DiscountCode>();
    ArrayList restuarunts = new ArrayList<Restaurunt>();

    protected DataBase() {

    }

    public static synchronized DataBase getInstance() {
        if (single_instance == null)
            single_instance = new DataBase();
        return single_instance;
    }

    public User getUsersByUsername(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (((User) allUsers.get(i)).getUsername().equals(username)) {
                return ((User) allUsers.get(i));
            }

        }
        return new User(null, null);
    }

    public Restaurunt getRestauruntByUsername(String username) {
        for (int i = 0; i < restuarunts.size(); i++) {
            if (((Restaurunt) restuarunts.get(i)).getUsername().equals(username)) {
                return ((Restaurunt) restuarunts.get(i));
            }
        }
        return new Restaurunt(null, null);
    }

    public ArrayList getRestuarunts() {
        return restuarunts;
    }

    public ArrayList getDiscountCode() {
        return discountCodes;
    }

    public ArrayList getAllUsers() {
        return allUsers;
    }

    public void addCustomer(Customer customer) {
        this.allUsers.add(customer);
    }

    public void addRestaurantAdmin(RestaurantAdmin restaurantAdmin) {
        this.allUsers.add(restaurantAdmin);
    }


    public void addSnapfoodAdmin(SnapfoodAdmin snapfoodAdmin) {
        this.allUsers.add(snapfoodAdmin);
    }

    public void addRestauratn(Restaurunt restaurunt) {
        this.restuarunts.add(restaurunt);
    }

    public void addDiscount(DiscountCode discountCode) {
        this.discountCodes.add(discountCode);
    }

    public void removeUser(User user) {
        this.allUsers.remove(user);
    }
    public void removeRestaurant(Restaurunt restaurunt) {
        this.restuarunts.remove(restaurunt);
    }
    public ArrayList getRestaurantByType(String type) {
        ArrayList rstauranList=new ArrayList<Restaurunt>();
        for (int i = 0; i < restuarunts.size(); i++) {
            if(((Restaurunt)restuarunts.get(i)).getType().equals(type)){
                rstauranList.add(((Restaurunt)restuarunts.get(i)));
            }
        }
        return rstauranList;
    }
    public void removeDiscountCode(DiscountCode discountCode) {
        this.discountCodes.remove(discountCode);
    }

}
