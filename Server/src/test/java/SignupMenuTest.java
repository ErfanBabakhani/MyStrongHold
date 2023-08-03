import org.example.view.SignupMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SignupMenuTest extends SignupMenu {
    @Test
    public void Test(){
        signupMatcher();
        createUserMatcherGroups();
    }
    @Test
    public void signupMatcher(){
        String regex="^user create(((?: -u (?<username>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))) (?<confirmPassword>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?<checkSlogan> -s (?<slogan>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -email (?<email>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,5}$";
        String command="user create -u sobhan -p password password -email sobhnaarshadi.officail@gmail.com -n nickname -s slogan";
        Assertions.assertNotNull(isMatched(command,regex));
        command="user create -u sobhan -p password password";
        Assertions.assertNotNull(isMatched(command,regex));
        command="user create -n nickname -u sobhan -p password password -s \"Down with Israel\" -email sobhnaarshadi.officail@gmail.com";
        Assertions.assertNotNull(isMatched(command,regex));
        command="user create -n nickname-u sobhan -p password password -s slogan -email sobhnaarshadi.officail@gmail.com";
        Assertions.assertNull(isMatched(command, regex));
    }
    @Test
    public void createUserMatcherGroups(){
        String regex="^user create(((?: -u (?<username>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?: -p (?<password>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))) (?<confirmPassword>(?:\"[^\"]*\")|(?:(?!\")\\S+(?<!\"))))|(?<checkSlogan> -s (?<slogan>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -n (?<nickname>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))|(?: -email (?<email>(?:\"[^\"]+\")|(?:(?!\")\\S+(?<!\"))))){0,1}){0,5}$";
        String command="user create -u sobhan -p password password -email sobhnaarshadi.officail@gmail.com -n nickname -s slogan";
        Assertions.assertEquals(isMatched(command,regex).group("username"),"sobhan");
    }
}
