package com.kinesio.service.internal.impl;

import com.kinesio.model.Appointment;
import com.kinesio.model.AppointmentStatus;
import com.kinesio.repository.AppointmentRepository;
import com.kinesio.service.internal.AppointmentService;
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

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Appointment findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Appointment appointment) {
        repository.save(appointment);
    }

    @Override
    @Transactional
    public List<Appointment> getAppointmentsToSync() {
        return repository.getAppointmentsToSync();
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
        repository.save(appointment);
    }
}
