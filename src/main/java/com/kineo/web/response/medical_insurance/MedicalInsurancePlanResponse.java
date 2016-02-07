package com.kineo.web.response.medical_insurance;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsurancePlanResponse {

    private Long id;

    public MedicalInsurancePlanResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
