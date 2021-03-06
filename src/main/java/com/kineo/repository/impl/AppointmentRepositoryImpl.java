package com.kineo.repository.impl;

import com.kineo.model.Appointment;
import com.kineo.model.CalendarEventStatus;
import com.kineo.repository.AppointmentRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
@Repository
public class AppointmentRepositoryImpl extends GenericRepository<Appointment> implements AppointmentRepository {

    @Autowired
    public AppointmentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Appointment> getAppointmentsToSync() {
        Criteria appointmentCriteria = getCriteria();
        Criteria eventCriteria = appointmentCriteria.createCriteria("event");
        eventCriteria.add(Restrictions.eq("status", CalendarEventStatus.PENDING));
        return appointmentCriteria.list();
    }
}
