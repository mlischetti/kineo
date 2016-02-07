package com.kineo.repository.impl;

import com.kineo.model.Patient;
import com.kineo.repository.PatientRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mlischetti on 12/7/15.
 */
@Repository
public class PatientRepositoryImpl extends GenericRepository<Patient> implements PatientRepository {

    @Autowired
    public PatientRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
