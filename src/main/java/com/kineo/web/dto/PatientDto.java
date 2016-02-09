package com.kineo.web.dto;

import com.kineo.model.Patient;

/**
 * Created by mlischetti on 12/7/15.
 */
public class PatientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long medicalInsurancePlanId;
    private String affiliateId;
    private String phone;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        if (patient.getMedicalInsurancePlan() != null) {
            this.medicalInsurancePlanId = patient.getMedicalInsurancePlan().getId();
        }
        this.affiliateId = patient.getAffiliateId();
        this.phone = patient.getPhone();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMedicalInsurancePlanId() {
        return medicalInsurancePlanId;
    }

    public void setMedicalInsurancePlanId(Long medicalInsurancePlanId) {
        this.medicalInsurancePlanId = medicalInsurancePlanId;
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
}
