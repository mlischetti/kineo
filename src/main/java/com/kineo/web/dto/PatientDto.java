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
    private MedicalInsurancePlanDto medicalInsurancePlan;
    private String affiliateId;
    private String phone;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        if (patient.getMedicalInsurancePlan() != null) {
            this.medicalInsurancePlan = new MedicalInsurancePlanDto(patient.getMedicalInsurancePlan());
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
