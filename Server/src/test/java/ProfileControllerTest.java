import org.example.controller.ProfileController;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileControllerTest extends ProfileController{
    @Test
    public void test(){
        changeNickNameTest();
        changeUsernameTest();
    }

    @Test
    public void changeNickNameTest() {
        logedInuser=new User("sobhan","123456789","haj soby");
        Pattern pattern=Pattern.compile("^profile change((?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}$");
        Matcher matcher=pattern.matcher("profile change -n sobhanallah");
        matcher.find();
        Assertions.assertEquals(changeNickName(matcher),"Nickname changed");
    }
    @Test
    public void changeUsernameTest() {
        Matcher matcher;
        logedInuser=new User("sobhan","123456789","haj soby");
        matcher= Pattern.compile("^profile change((?: -u (?<username>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){1}$").matcher("profile change -u arshadi");
        matcher.find();
        Assertions.assertEquals(changeUsername(matcher),"Username changed");
    }
}
