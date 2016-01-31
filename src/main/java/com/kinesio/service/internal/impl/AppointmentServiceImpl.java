package com.kinesio.service.internal.impl;

import com.kinesio.model.Appointment;
import com.kinesio.model.AppointmentStatus;
import com.kinesio.model.CalendarEventStatus;
import com.kinesio.repository.AppointmentRepository;
import com.kinesio.service.external.CalendarService;
import com.kinesio.service.internal.AppointmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository repository;
    private CalendarService calendarService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository, CalendarService calendarService) {
        this.repository = repository;
        this.calendarService = calendarService;
    }

    @Override
    @Transactional
    public Appointment findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Appointment appointment) {
        String eventId = calendarService.submit(appointment);
        if (StringUtils.isNotBlank(eventId)) {
            appointment.getEvent().setEventId(eventId);
            appointment.getEvent().setStatus(CalendarEventStatus.SYNC);
        }
        repository.save(appointment);
    }

    @Override
    @Transactional
    public List<Appointment> find(Date startTime, Date endTime, int firstResult, int maxResults) {
        //TODO: Implement me!
        return null;
    }

    @Override
    @Transactional
    public Long count(Date startTime, Date endTime) {
        //TODO: Implement me!
        return null;
    }

    @Override
    @Transactional
    public void delete(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.DELETE);
        calendarService.delete(appointment);
        appointment.getEvent().setStatus(CalendarEventStatus.SYNC);
        repository.save(appointment);
    }
}
