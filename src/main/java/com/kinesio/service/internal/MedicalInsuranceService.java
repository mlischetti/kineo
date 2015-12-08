package com.kinesio.service.internal;

import com.kinesio.model.MedicalInsuranceCompany;
import com.kinesio.model.MedicalInsurancePlan;

/**
 * Created by mlischetti on 12/8/15.
 */
public interface MedicalInsuranceService {

    MedicalInsuranceCompany findCompanyById(Long id);

    void save(MedicalInsuranceCompany company);

    MedicalInsurancePlan findPlanById(Long planId);

    void save(MedicalInsurancePlan plan);
}
