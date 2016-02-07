package com.kineo.web.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends RuntimeException {

    private List<ErrorField> fields = new ArrayList<>();

    public ValidationException() {
        super();
    }

    public ValidationException errorField(ErrorField field) {
        this.fields.add(field);
        return this;
    }

    public ValidationException errorField(String field, String message) {
        return this.errorField(new ErrorField(field, message));
    }

    public List<ErrorField> getFields() {
        return fields;
    }
}
