package com.mozu.sterling.handler;

import java.net.URLEncoder;
import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final String FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    
    public static DateTime convertFromSterlingTime(String lscTimeString) {
        DateTime dt = null;

        if (lscTimeString!=null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_PATTERN);
            dt = formatter.parseDateTime(lscTimeString);
        }
        return dt;
    }

    public static String currentDateString() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_PATTERN);
        DateTime dt = new DateTime();
        return formatter.print(dt);
    }
    
    public static String convertFromMozuTime(DateTime mozuDateTime) {
        if (mozuDateTime!=null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_PATTERN);
            return formatter.print(mozuDateTime);
        } else {
            return null;
        }
    }
    
    public static String urlSafeFormat(Timestamp timeStamp) throws Exception {
        String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss z";
        DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_PATTERN);
        String stString = formatter.print(new DateTime(timeStamp.getTime()));
        return URLEncoder.encode(stString, "UTF-8");
    }
}
