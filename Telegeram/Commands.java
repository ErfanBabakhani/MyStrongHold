import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commands {
    private String regex;

    private Commands(String regex) {
        this.regex=regex;
    }

    // public Commands(String regex,String inpuString) {
    //     this.regex=regex;
    // }

    public Matcher getMacher(String input, Commands commands) {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(input);
        return matcher;
    }
}
