package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateString {
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date stringToDate(String string) {
        // todo implement
        return null;
    }
}
