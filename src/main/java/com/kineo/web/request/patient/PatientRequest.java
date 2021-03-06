package com.kineo.web.request.patient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by mlischetti on 12/7/15.
 */
public class PatientRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    private String affiliateId;

    private Long medicalInsurancePlanId;

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

    public String getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(String affiliateId) {
        this.affiliateId = affiliateId;
    }

    public Long getMedicalInsurancePlanId() {
        return medicalInsurancePlanId;
    }

    public void setMedicalInsurancePlanId(Long medicalInsurancePlanId) {
        this.medicalInsurancePlanId = medicalInsurancePlanId;
    }
}
