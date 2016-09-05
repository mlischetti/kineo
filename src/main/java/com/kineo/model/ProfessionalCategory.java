package com.kineo.model;

/**
 * Created by mlischetti on 03/09/16.
 */
public enum ProfessionalCategory {
    LIC(0, "Lic", "Licenciado"),
    DR(1, "Dr", "Doctor");

    private int code;
    private String shortDescription;
    private String description;

    ProfessionalCategory(int code, String shortDescription, String description) {
        this.code = code;
        this.shortDescription = shortDescription;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }
}
