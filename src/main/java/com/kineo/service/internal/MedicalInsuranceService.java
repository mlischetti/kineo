package com.kineo.service.internal;

import com.kineo.model.MedicalInsuranceCompany;
import com.kineo.model.MedicalInsurancePlan;

import java.util.List;

/**
 * Created by mlischetti on 12/8/15.
 */
public interface MedicalInsuranceService {

    MedicalInsuranceCompany findCompanyById(Long id);

    List<MedicalInsuranceCompany> findCompanies(int firstResult, int maxResults);

    Long countCompanies();

    void save(MedicalInsuranceCompany company);

    MedicalInsurancePlan findPlanById(Long planId);

    List<MedicalInsurancePlan> findPlans(int firstResult, int maxResults);

    Long countPlans();

    void save(MedicalInsurancePlan plan);
}
