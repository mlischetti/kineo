package com.kineo.web.dto;

import com.kineo.model.Professional;
import com.kineo.util.date.DateUtils;

/**
 * Created by mlischetti on 12/7/15.
 */
public class ProfessionalDto {

    private Long id;
    private String category;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String documentType;
    private String documentNumber;

    public ProfessionalDto(Professional professional) {
        this.id = professional.getId();
        this.category = professional.getCategory().name();
        this.firstName = professional.getFirstName();
        this.lastName = professional.getLastName();
        this.dateOfBirth = DateUtils.printAsSimpleDate(professional.getDateOfBirth());
        this.email = professional.getEmail();
        this.phone = professional.getPhone();
        if (professional.getDocumentType() != null) {
            this.documentType = professional.getDocumentType().name();
        }
        this.documentNumber = professional.getDocumentNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
