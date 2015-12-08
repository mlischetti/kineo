package com.kinesio.web.exception;

/**
 * Created by mlischetti on 12/7/15.
 */
public class ErrorField {

    private String field;

    private String message;

    public ErrorField() {

    }

    public ErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
