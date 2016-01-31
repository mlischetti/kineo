package com.kinesio.web.dto;

import com.kinesio.model.MedicalInsuranceCompany;

/**
 * Created by mlischetti on 12/8/15.
 */
public class MedicalInsuranceCompanyDto {
    private Long id;
    private String name;

    public MedicalInsuranceCompanyDto(MedicalInsuranceCompany company) {
        this.id = company.getId();
        this.name = company.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
