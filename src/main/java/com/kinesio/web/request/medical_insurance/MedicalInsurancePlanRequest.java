package com.kinesio.web.request.medical_insurance;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsurancePlanRequest {

    @NotNull
    @JsonProperty(value = "company_id")
    private Long companyId;

    @NotEmpty
    private String plan;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
