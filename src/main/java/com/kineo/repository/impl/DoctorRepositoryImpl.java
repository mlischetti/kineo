package com.kineo.repository.impl;

import com.kineo.model.Doctor;
import com.kineo.repository.DoctorRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mlischetti on 11/29/15.
 */
@Repository
public class DoctorRepositoryImpl extends GenericRepository<Doctor> implements DoctorRepository {

    @Autowired
    public DoctorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
