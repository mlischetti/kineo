package com.kineo.web.dto;

import com.kineo.model.Patient;
import com.kineo.util.date.DateUtils;

/**
 * Created by mlischetti on 12/7/15.
 */
public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String documentType;
    private String documentNumber;
    private MedicalInsurancePlanDto medicalInsurancePlan;
    private String affiliateId;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.dateOfBirth = DateUtils.printAsSimpleDate(patient.getDateOfBirth());
        this.email = patient.getEmail();
        this.phone = patient.getPhone();
        if(patient.getDocumentType() != null) {
            this.documentType = patient.getDocumentType().name();
        }
        this.documentNumber = patient.getDocumentNumber();
        if (patient.getMedicalInsurancePlan() != null) {
            this.medicalInsurancePlan = new MedicalInsurancePlanDto(patient.getMedicalInsurancePlan());
        }
        this.affiliateId = patient.getAffiliateId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MedicalInsurancePlanDto getMedicalInsurancePlan() {
        return medicalInsurancePlan;
    }

    public void setMedicalInsurancePlan(MedicalInsurancePlanDto medicalInsurancePlan) {
        this.medicalInsurancePlan = medicalInsurancePlan;
    }

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

}
