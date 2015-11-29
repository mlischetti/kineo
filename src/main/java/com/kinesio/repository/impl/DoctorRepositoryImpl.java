package com.kinesio.repository.impl;

import com.kinesio.model.Doctor;
import com.kinesio.repository.DoctorRepository;
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
