package com.kinesio.repository.impl;

import com.kinesio.model.Appointment;
import com.kinesio.model.AppointmentStatus;
import com.kinesio.repository.AppointmentRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Mariano on 1/24/2016.
 */
@Repository
public class AppointmentRepositoryImpl extends GenericRepository<Appointment> implements AppointmentRepository {

    @Autowired
    public AppointmentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
