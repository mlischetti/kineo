package com.kineo.util.date;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mariano on 1/30/2016.
 */
public class SupportedDatesFormat extends DateFormat {

    private static final long serialVersionUID = 1L;

    private DateFormat isoFormat = new ISO8601DateFormat();
    private DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return this.isoFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        try {
            Date d = this.isoFormat.parse(source);
            pos.setIndex(pos.getIndex() + source.length());
            return d;
        } catch (Exception e) {
            try {
                if (source.length() != 10) {
                    throw new IllegalArgumentException("Not a 'yyyy-MM-dd'");
                }
                Date d2 = this.simpleDateFormat.parse(source);
                pos.setIndex(pos.getIndex() + source.length());
                return d2;
            } catch (Exception e1) {
                throw new IllegalArgumentException(String.format("Can't parse %s as Date. Expected formats are 'yyyy-MM-dd' and 'yyyy-MM-ddThh:mmm:ssZ'", source));
            }
        }
    }

    @Override
    public void setTimeZone(TimeZone timeZone) {
        this.isoFormat.setTimeZone(timeZone);
        this.simpleDateFormat.setTimeZone(timeZone);
    }

    @Override
    public TimeZone getTimeZone() {
        return this.isoFormat.getTimeZone();
    }

    @Override
    public Object clone() {
        SupportedDatesFormat format = new SupportedDatesFormat();
        format.setTimeZone(this.getTimeZone());
        return format;
    }
}
