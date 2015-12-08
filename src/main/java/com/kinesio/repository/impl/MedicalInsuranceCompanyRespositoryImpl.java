package com.kinesio.repository.impl;

import com.kinesio.model.MedicalInsuranceCompany;
import com.kinesio.repository.MedicalInsuranceCompanyRepository;
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
