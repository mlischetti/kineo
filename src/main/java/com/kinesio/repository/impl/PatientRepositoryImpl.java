package com.kinesio.repository.impl;

import com.kinesio.model.Patient;
import com.kinesio.repository.PatientRepository;
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
