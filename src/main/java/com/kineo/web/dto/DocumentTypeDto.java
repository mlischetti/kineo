package com.kineo.web.dto;

import com.kineo.model.DocumentType;

/**
 * Created by mlischetti on 30/04/16.
 */
public class DocumentTypeDto {

    private String name;
    private String description;

    public DocumentTypeDto(DocumentType documentType) {
        this.name = documentType.name();
        this.description = documentType.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
