package com.kinesio.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by mlischetti on 12/8/15.
 */
@Entity
@Table(name = "Medical_Insurance")
public class MedicalInsurance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "medical_insurance_plan_id")
    private MedicalInsurancePlan medicalInsurancePlan;

    @Column(name = "affiliate_id")
    @NotBlank
    private String affiliateId;

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
}
