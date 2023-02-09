package database;

import java.util.regex.Pattern;

public class RegexValidator {
    public static boolean isValidUsername(String address){
        String usernameRegex = "^\\S+$";
            return Pattern.matches(usernameRegex, address);

    }
}