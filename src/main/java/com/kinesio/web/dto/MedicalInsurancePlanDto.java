package com.kinesio.web.dto;

import com.kinesio.model.MedicalInsurancePlan;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsurancePlanDto {
    private MedicalInsuranceCompanyDto company;
    private String plan;

    public MedicalInsurancePlanDto(MedicalInsurancePlan plan) {
        this.company = new MedicalInsuranceCompanyDto(plan.getCompany());
        this.plan = plan.getPlan();
    }

    public MedicalInsuranceCompanyDto getCompany() {
        return company;
    }

    public void setCompany(MedicalInsuranceCompanyDto company) {
        this.company = company;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
