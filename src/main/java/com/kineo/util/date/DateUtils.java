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

    public static String printAsSimpleDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return DATE_FORMAT.print(dateTime);
    }
}
