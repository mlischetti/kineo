package com.kinesio.service.internal.impl;

import com.kinesio.model.MedicalInsuranceCompany;
import com.kinesio.model.MedicalInsurancePlan;
import com.kinesio.repository.MedicalInsuranceCompanyRepository;
import com.kinesio.repository.MedicalInsurancePlanRepository;
import com.kinesio.service.internal.MedicalInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<MedicalInsuranceCompany> findCompanies(int firstResult, int maxResults) {
        return companyRepository.find(firstResult, maxResults);
    }

    @Override
    public Long countCompanies() {
        return companyRepository.count();
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
    public List<MedicalInsurancePlan> findPlans(int firstResult, int maxResults) {
        return planRepository.find(firstResult, maxResults);
    }

    @Override
    public Long countPlans() {
        return planRepository.count();
    }

    @Override
    @Transactional
    public void save(MedicalInsurancePlan plan) {
        planRepository.save(plan);
    }
}
