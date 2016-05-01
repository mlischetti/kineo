package com.kineo.model;

/**
 * Created by mlischetti on 4/6/16.
 */
public enum DocumentType {
    DNI("Documento nacional de identidad"),
    LE("Libreta de enrolamiento"),
    LC("Libreta civica"),
    CI("Cedula de identidad");

    private String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
