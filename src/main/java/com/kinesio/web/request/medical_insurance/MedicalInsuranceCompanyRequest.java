package com.kinesio.web.request.medical_insurance;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsuranceCompanyRequest {

    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
