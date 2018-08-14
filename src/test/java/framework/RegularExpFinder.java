package framework;
import java.util.regex.*;

public class RegularExpFinder {

    private static Pattern pattern;
    private static Matcher matcher;
    private static String value;

    public static String findByRegularExp(String string, String regExp){

        pattern = Pattern.compile(regExp);
        matcher = pattern.matcher(string);
        matcher.find();
        value = matcher.group(1);
        return value;
    }
}
