package com.kineo.web.dto;

import com.kineo.model.MedicalInsurancePlan;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsurancePlanDto {
    private MedicalInsuranceCompanyDto company;
    private Long id;
    private String plan;

    public MedicalInsurancePlanDto(MedicalInsurancePlan plan) {
        this.company = new MedicalInsuranceCompanyDto(plan.getCompany());
        this.id = plan.getId();
        this.plan = plan.getPlan();
    }

    public MedicalInsuranceCompanyDto getCompany() {
        return company;
    }

    public void setCompany(MedicalInsuranceCompanyDto company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
