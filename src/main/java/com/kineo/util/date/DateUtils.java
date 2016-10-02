package com.kineo.util.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ARG_DATE_TIME_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");

    public static DateTime parseAsSimpleDateTime(String date) {
        DateTime output = null;
        if (date != null) {
            output = DATE_FORMAT.parseDateTime(date);
        }
        return output;
    }

    public static String printDateTime(DateTime dateTime) {
        String output = null;
        if (dateTime != null) {
            output = dateTime.toString();
        }
        return output;
    }

    public static String printAsSimpleDate(DateTime dateTime) {
        String output = null;
        if (dateTime != null) {
            output = DATE_FORMAT.print(dateTime);
        }
        return output;
    }

    public static String printAsSimpleDate(Date date) {
        String output = null;
        if (date != null) {
            output =  printAsSimpleDate(new DateTime(date));
        }
        return output;
    }

    public static String printAsArgDateTime(DateTime dateTime) {
        String output = null;
        if (dateTime != null) {
            output = ARG_DATE_TIME_FORMAT.print(dateTime);
        }
        return output;
    }
}
