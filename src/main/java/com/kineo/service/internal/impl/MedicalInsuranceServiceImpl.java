package com.kineo.service.internal.impl;

import com.kineo.model.MedicalInsuranceCompany;
import com.kineo.model.MedicalInsurancePlan;
import com.kineo.repository.MedicalInsuranceCompanyRepository;
import com.kineo.repository.MedicalInsurancePlanRepository;
import com.kineo.service.internal.MedicalInsuranceService;
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
    @Transactional
    public List<MedicalInsuranceCompany> findCompanies(int firstResult, int maxResults) {
        return companyRepository.find(firstResult, maxResults);
    }

    @Override
    @Transactional
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
    public void delete(MedicalInsuranceCompany company) {
        company.setDeleted(true);
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public MedicalInsurancePlan findPlanById(Long planId) {
        return planRepository.findById(planId);
    }

    @Override
    @Transactional
    public List<MedicalInsurancePlan> findPlans(int firstResult, int maxResults) {
        return planRepository.find(firstResult, maxResults);
    }

    @Override
    @Transactional
    public Long countPlans() {
        return planRepository.count();
    }

    @Override
    @Transactional
    public void save(MedicalInsurancePlan plan) {
        planRepository.save(plan);
    }

    @Override
    @Transactional
    public void delete(MedicalInsurancePlan plan) {
        plan.setDeleted(true);
        planRepository.save(plan);
    }
}
