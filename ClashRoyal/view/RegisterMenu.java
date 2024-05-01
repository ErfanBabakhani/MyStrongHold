package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.*;
import controller.*;

import model.DataBase;

public class RegisterMenu extends MainView {
    public boolean mainMenuStatus;
    public User loginedUser;
    private static DataBase dataBase = DataBase.getInstance();
    private static String menuName = "Register/Login Menu";
    static Pattern[] patterns = new Pattern[4];
    static Pattern[] subPatterns = new Pattern[4];
    static {
        patterns[0] = Pattern.compile("^show current menu$");
        // I dont know space in username is inavalid command or wrong format(Suppose
        // wrong format)
        patterns[1] = Pattern.compile("^register username (.+) password (.+)$");
        patterns[2] = Pattern.compile("^login username (.+) password (.+)$");
        patterns[3] = Pattern.compile("^Exit$");
        // for register command
        subPatterns[0] = Pattern.compile("^register username ([A-Za-z]+) password (.+)$");
        subPatterns[1] = Pattern.compile(
                "^register username ([A-Za-z]+) password ((?=[^\\s]{8,20}$)(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])(?=[^\\s]*[\\!\\@\\#\\$\\%\\^\\&\\*])[^\\s\\d][^\\s]*)$");
        // for login
        subPatterns[2] = Pattern.compile("^login username ([A-Za-z]+) password (.+)$");
        subPatterns[3] = Pattern.compile(
                "^login username ([A-Za-z]+) password ((?=[^\\s]{8,20}$)(?=[^\\s]*[A-Z])(?=[^\\s]*[a-z])(?=[^\\s]*[0-9])(?=[^\\s]*[\\!\\@\\#\\$\\%\\^\\&\\*])[^\\s\\d][^\\s]*)$");

    }

    public void run(Scanner scanner) {
        String userInput=scanner.nextLine();
        this.mainMenuStatus=false;
        userInput=userInput.replaceAll("\t", " ");
        boolean checkCommandsTruth = false;
        Matcher matcher=patterns[0].matcher(userInput);
        int i;
        for (i = 0; i < patterns.length; i++) {
            matcher=patterns[i].matcher(userInput);
            if(matcher.find()){
                checkCommandsTruth=true;
                break;
            }
        }
        if(!checkCommandsTruth){
            System.out.println("Invalid command!");
            return;
        }else{
            if(i==0){
                System.out.println(this.menuName);
                return;
            }else if(i==1){
                matcher=subPatterns[0].matcher(userInput);
                if(matcher.find()){
                    matcher=subPatterns[1].matcher(userInput);
                    if(matcher.find()){
                        if(mainController.getUserByUsername(matcher.group(1)).getUsername()!=null){
                            System.out.println("Username already exists!");
                            return;
                        }else{
                            addUserToDataBase(createUser(matcher));
                            System.out.println("User "+matcher.group(1)+" created successfully!");
                            return;
                        }
                    }else{
                        System.out.println("Incorrect format for password!");
                        return;
                    }
                }else{
                    System.out.println("Incorrect format for username!");
                    return;
                }
            }else if(i==2){
                matcher=subPatterns[2].matcher(userInput);
                if(matcher.find()){
                    matcher=subPatterns[3].matcher(userInput);
                    if(matcher.find()){
                        User user=mainController.getUserByUsername(matcher.group(1));
                        if(user.getUsername()==null){
                            System.out.println("Username doesn't exist!");
                            return;
                        }else if(!user.getPassword().equals(matcher.group(2))){
                            System.out.println("Password is incorrect!");
                            return;
                        }else{
                            this.mainMenuStatus=true;
                            this.loginedUser=user;
                            System.out.println("User "+matcher.group(1)+" logged in!");
                            return;
                        }
                    }else{
                        System.out.println("Incorrect format for password!");
                        return;
                    }
                }else{
                    System.out.println("Incorrect format for username!");
                    return;
                }
            }else if(i==3){
                System.exit(0);
            }
        }
    
    }

    public void addUserToDataBase(User user) {
        this.dataBase.addUser(user);
    }

    public User createUser(Matcher matcher) {
        User user = new User(matcher.group(1), matcher.group(2));
        return user;
    }
}
