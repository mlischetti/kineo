package com.kineo.web.dto;

import com.kineo.model.ProfessionalCategory;

/**
 * Created by mlischetti on 04/09/16.
 */
public class ProfessionalCategoryDto {

    private String name;
    private String shortDescription;
    private String description;

    public ProfessionalCategoryDto(ProfessionalCategory professionalCategory) {
        this.name = professionalCategory.name();
        this.shortDescription = professionalCategory.getShortDescription();
        this.description = professionalCategory.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
