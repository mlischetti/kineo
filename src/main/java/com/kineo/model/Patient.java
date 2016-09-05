package com.kineo.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mlischetti on 11/27/15.
 */
@Entity
@Table(name = "PATIENT")
public class Patient extends BaseEntity {

    public static final String ENTITY = "PATIENT";

    @Column(name = "DELETED")
    private boolean deleted = false;

    @Column(name = "FIRST_NAME")
    @NotEmpty
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotEmpty
    private String lastName;

    @Column(name = "DATE_OF_BIRTH", nullable = false)
    @Type(type = "date")
    private Date dateOfBirth;

    @Column(name = "EMAIL")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @ManyToOne
    @JoinColumn(name = "MEDICAL_INSURANCE_PLAN_ID")
    private MedicalInsurancePlan medicalInsurancePlan;

    @Column(name = "AFFILIATE_ID")
    private String affiliateId;

    @Column(name = "PHONE")
    private String phone;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public MedicalInsurancePlan getMedicalInsurancePlan() {
        return medicalInsurancePlan;
    }

    public void setMedicalInsurancePlan(MedicalInsurancePlan medicalInsurancePlan) {
        this.medicalInsurancePlan = medicalInsurancePlan;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
