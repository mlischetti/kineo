package com.kineo.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mlischetti on 11/27/15.
 */

@Entity
@Table(name = "PROFESSIONAL")
public class Professional extends BaseEntity {

    public static final String ENTITY = "PROFESSIONAL";

    @Column(name = "DELETED", nullable = false)

    private boolean deleted = false;

    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfessionalCategory category;

    @Column(name = "FIRST_NAME", nullable = false)
    @NotEmpty
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @NotEmpty
    private String lastName;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Type(type = "date")
    private Date dateOfBirth;

    @Column(name = "EMAIl", nullable = false)
    @NotEmpty
    private String email;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    private DocumentType documentType;

    @Column(name = "DOCUMENT_NUMBER", nullable = false)
    private String documentNumber;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ProfessionalCategory getCategory() {
        return category;
    }

    public void setCategory(ProfessionalCategory category) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

}
