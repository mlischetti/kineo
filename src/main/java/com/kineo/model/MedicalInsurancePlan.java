package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by mlischetti on 12/8/15.
 */

@Entity
@Table(name = "Medical_Insurance_Plan")
public class MedicalInsurancePlan extends BaseEntity {

    public static final String ENTITY = "Medical_Insurance_Plan";

    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private MedicalInsuranceCompany company;

    @NotEmpty
    @Column(name = "plan", nullable = false)
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
