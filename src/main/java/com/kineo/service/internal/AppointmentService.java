package com.kineo.service.internal;

import com.kineo.model.Appointment;

import java.util.Date;
import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
public interface AppointmentService {

    Appointment findById(Long id);

    void save(Appointment appointment);

    List<Appointment> getAppointmentsToSync();

    List<Appointment> find(Date startTime, Date endTime, int firstResult, int maxResults);

    Long count(Date startTime, Date endTime);

    void delete(Appointment appointment);
}
