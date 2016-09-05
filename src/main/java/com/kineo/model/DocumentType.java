package com.kineo.model;

/**
 * Created by mlischetti on 4/6/16.
 */
public enum DocumentType {
    DNI(0, "Documento nacional de identidad"),
    LE(1, "Libreta de enrolamiento"),
    LC(2, "Libreta civica"),
    CI(3, "Cedula de identidad");

    private int code;
    private String description;

    DocumentType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
