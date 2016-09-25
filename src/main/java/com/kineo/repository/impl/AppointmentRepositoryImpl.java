package com.kineo.repository.impl;

import com.kineo.model.Appointment;
import com.kineo.model.CalendarEventStatus;
import com.kineo.repository.AppointmentRepository;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
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
    public List<Appointment> find(DateTime since, DateTime until, String professional, String patient) {
        Criteria appointmentCriteria = getCriteria();
        if (since != null) {
            appointmentCriteria.add(Restrictions.gt("startTime", since));
        }
        if (until != null) {
            appointmentCriteria.add(Restrictions.le("startTime", until));
        }
        if (StringUtils.isNotEmpty(professional)) {
            Criteria professionalCriteria = appointmentCriteria.createCriteria("professional");
            professionalCriteria.add(Restrictions.like("lastName", professional, MatchMode.START));
        }
        if (StringUtils.isNotEmpty(patient)) {
            Criteria patientCriteria = appointmentCriteria.createCriteria("patient");
            patientCriteria.add(Restrictions.like("lastName", patient, MatchMode.START));
        }
        return appointmentCriteria.list();
    }

    @Override
    public List<Appointment> getAppointmentsToSync() {
        Criteria appointmentCriteria = getCriteria();
        Criteria eventCriteria = appointmentCriteria.createCriteria("event");
        eventCriteria.add(Restrictions.eq("status", CalendarEventStatus.PENDING));
        return appointmentCriteria.list();
    }
}
