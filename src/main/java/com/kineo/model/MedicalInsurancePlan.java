package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by mlischetti on 12/8/15.
 */

@Entity
@Table(name = "Medical_Insurance_Plan")
public class MedicalInsurancePlan extends BaseEntity {

    public static final String ENTITY = "Medical_Insurance_Plan";

    @ManyToOne
    @JoinColumn(name = "company_id")
    private MedicalInsuranceCompany company;

    @NotEmpty
    private String plan;

    public MedicalInsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(MedicalInsuranceCompany company) {
        this.company = company;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
