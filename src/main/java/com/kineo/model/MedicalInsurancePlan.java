package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by mlischetti on 12/8/15.
 */

@Entity
@Table(name = "MEDICAL_INSURANCE_PLAN")
public class MedicalInsurancePlan extends BaseEntity {

    public static final String ENTITY = "MEDICAL_INSURANCE_PLAN";

    @Column(name = "DELETED", nullable = false)
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private MedicalInsuranceCompany company;

    @NotEmpty
    @Column(name = "PLAN", nullable = false)
    private String plan;

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public MedicalInsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(MedicalInsuranceCompany company) {
        this.company = company;
    }

    public String getPlan() {
        return plan;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
