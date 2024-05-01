import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    static private HashMap idToPassword = new HashMap<String, String>();
    public boolean loginStatus;
    private User theLastRegisterdUser;
    public User theLoginedUser;    

    public void run(Scanner scanner) {
        
        loginStatus=false;
        String a = scanner.nextLine();
        
        if (a.equals("exit")) {
            System.exit(0);
        }
        

        Pattern pattern2 = Pattern.compile("^register i [^\\s]+ u [^\\s]+ p [^\\s]+$");
        Matcher matcher2 = pattern2.matcher(a);

        Pattern pattern = Pattern.compile(
                "^register i ([^\\s]+) u ([_A-Za-z0-9]+) p ((?=.{8,32}$)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\*\\.\\!\\@\\$\\%\\^\\&\\(\\)\\{\\}\\[\\]\\:\\;\\<\\>\\,\\?\\/\\~\\_\\+\\-\\=\\|]).+)$");
        Matcher matcher = pattern.matcher(a);
        

        Pattern patterna = Pattern.compile("^login i ([^\\s]+) p ([^\\s]+)$");
        Matcher matchera = patterna.matcher(a);
        
        boolean isMatch = matchera.find();

        if (!isMatch) {

            if (!matcher2.find()) {
                System.out.println("Invalid command!");
                return;
            } else {
                Pattern pattern3 = Pattern.compile("^register i [^\\s]+ u [_A-Za-z0-9]+ ");
                Matcher matcher3 = pattern3.matcher(a);
                if (!matcher3.find()) {
                    System.out.println("Username's format is invalid!");
                    return;
                } else if (!matcher.find()) {
                    System.out.println("Password is weak!");
                    return;
                }
            }
            System.out.println(this.register(matcher));
            return;
        } else if (isMatch) {
            System.out.println(this.login(matchera));
            return;
        } else {
            System.out.println("OOps");
        }

    }

    private String login(Matcher mathcer) {

        if (!idToPassword.containsKey(mathcer.group(1))) {
            return "No user with this id exists!";
        } else if (!idToPassword.get(mathcer.group(1)).equals(mathcer.group(2))) {
            return "Incorrect password!";
        } else {
            loginStatus=true;
            theLoginedUser=theLoginedUser.messenger.getUserBuyId(mathcer.group(1));
            return "User successfully logged in!";
        }
    }

    private String register(Matcher matcher) {

        if (idToPassword.containsKey(matcher.group(1))) {
            return "A user with this ID already exists!";
        } else {

            idToPassword.put(matcher.group(1), matcher.group(3));
            theLastRegisterdUser=new User(matcher.group(1), matcher.group(2), matcher.group(3));
            theLastRegisterdUser.messenger.addUser(theLastRegisterdUser);
            // System.out.println("id="+theLastRegisterdUser.getId()+"name="+theLastRegisterdUser.getName());
            return "User has been created successfully!";
        }
        
    }

    

    // public static void main(String[] args) {
    //     LoginMenu loginMenu=new LoginMenu();
    //     Scanner scanner=new Scanner(System.in);
    //     while (true) {
    //         loginMenu.run(scanner);            
    //     }    
    // }
    
}
