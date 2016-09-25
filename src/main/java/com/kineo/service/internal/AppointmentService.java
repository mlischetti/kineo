package com.kineo.service.internal;

import com.kineo.model.Appointment;
import com.kineo.model.AppointmentStatus;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
public interface AppointmentService {

    Appointment findById(Long id);

    void save(Appointment appointment);

    List<Appointment> getAppointmentsToSync();

    List<Appointment> find(AppointmentStatus status, DateTime since, DateTime until, String professional, String patient);

    void delete(Appointment appointment);
}
