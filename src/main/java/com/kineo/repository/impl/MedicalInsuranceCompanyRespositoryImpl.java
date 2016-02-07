package com.kineo.repository.impl;

import com.kineo.model.MedicalInsuranceCompany;
import com.kineo.repository.MedicalInsuranceCompanyRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mlischetti on 12/8/15
 */
@Repository
public class MedicalInsuranceCompanyRespositoryImpl extends GenericRepository<MedicalInsuranceCompany> implements MedicalInsuranceCompanyRepository {

    @Autowired
    public MedicalInsuranceCompanyRespositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
