package com.kineo.service.internal.impl;

import com.kineo.model.Appointment;
import com.kineo.model.AppointmentStatus;
import com.kineo.repository.AppointmentRepository;
import com.kineo.service.internal.AppointmentService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentServiceImpl.class);

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
    public List<Appointment> find(AppointmentStatus status, DateTime since, DateTime until, String professional, String patient) {
        LOGGER.info("Searching appointments - status: {}, since: {}, until: {}, professional: {}, patient: {}", status, since, until, professional, patient);
        return repository.find(status, since, until, professional, patient);
    }

    @Override
    @Transactional
    public void delete(Appointment appointment) {
        appointment.markAsDeleted();
        repository.save(appointment);
    }
}
