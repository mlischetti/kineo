package com.kineo.repository.impl;

import com.kineo.model.MedicalInsurancePlan;
import com.kineo.repository.MedicalInsurancePlanRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mlischetti on 12/8/15.
 */
@Repository
public class MedicalInsurancePlanRepositoryImpl extends GenericRepository<MedicalInsurancePlan> implements MedicalInsurancePlanRepository {

    @Autowired
    public MedicalInsurancePlanRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
