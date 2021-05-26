package com.ljscode.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date ToDate(String dateStr) {
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String ToString(Date date) {
        return sdf.format(date);
    }

    public static boolean LessThan(Date date1, Date date2) {
        return date1.compareTo(date2) < 0;
    }

    public static boolean GreaterThan(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    public static boolean EqualTo(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    public static boolean LessEqualTo(Date date1, Date date2) {
        return date1.compareTo(date2) <= 0;
    }

    public static boolean GreaterEqualTo(Date date1, Date date2) {
        return date1.compareTo(date2) >= 0;
    }

}
