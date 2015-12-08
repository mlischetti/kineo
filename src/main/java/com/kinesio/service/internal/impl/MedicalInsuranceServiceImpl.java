package com.kinesio.service.internal.impl;

import com.kinesio.model.MedicalInsuranceCompany;
import com.kinesio.model.MedicalInsurancePlan;
import com.kinesio.repository.MedicalInsuranceCompanyRepository;
import com.kinesio.repository.MedicalInsurancePlanRepository;
import com.kinesio.service.internal.MedicalInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by mlischetti on 12/8/15.
 */
@Service
public class MedicalInsuranceServiceImpl implements MedicalInsuranceService {

    private MedicalInsurancePlanRepository planRepository;
    private MedicalInsuranceCompanyRepository companyRepository;

    @Autowired
    public MedicalInsuranceServiceImpl(MedicalInsurancePlanRepository planRepository, MedicalInsuranceCompanyRepository companyRepository) {
        this.planRepository = planRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public MedicalInsuranceCompany findCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(MedicalInsuranceCompany company) {
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public MedicalInsurancePlan findPlanById(Long planId) {
        return planRepository.findById(planId);
    }

    @Override
    @Transactional
    public void save(MedicalInsurancePlan plan) {
        planRepository.save(plan);
    }
}
