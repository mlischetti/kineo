package com.kinesio.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by mlischetti on 11/27/15.
 */
@Entity
@Table(name = "Patient")
public class Patient extends BaseEntity {

    public static final String ENTITY = "Patient";

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    private String email;

    @ManyToOne
    @JoinColumn(name = "medical_insurance_plan_id")
    private MedicalInsurancePlan medicalInsurancePlan;

    @Column(name = "affiliate_id")
    private String affiliateId;

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

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
