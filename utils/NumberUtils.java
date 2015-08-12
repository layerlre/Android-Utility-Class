package utils;
/**
 * Created by layer on 2/7/2015.
 */
public class NumberUtils {
    public static boolean isIntegerParseInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;

        //return isNumericArray(str);
    }

    public static boolean isIntegerParseDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }

    public static boolean isNumericArray(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray())
            if (!Character.isDigit(c))
                return false;
        return true;
    }
}
