package com.kineo.util.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by mlischetti on 06/05/16.
 */
public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static DateTime parseAsSimpleDateTime(String date) {
        DateTime dateTime = null;
        if (date != null) {
            dateTime = DATE_FORMAT.parseDateTime(date);
        }
        return dateTime;
    }

    public static String printDateTime(DateTime dateTime) {
        String date = null;
        if (dateTime != null) {
            date = dateTime.toString();
        }
        return date;
    }


    public static String printAsSimpleDate(DateTime dateTime) {
        String date = null;
        if (dateTime != null) {
            date = DATE_FORMAT.print(dateTime);
        }
        return date;
    }

    public static String printAsSimpleDate(Date date) {
        if (date != null) {
            return printAsSimpleDate(new DateTime(date));
        }
        return null;
    }
}
