package com.kineo.service.internal;

import com.kineo.model.Appointment;
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

    List<Appointment> find(DateTime since, DateTime until, Long professionalId, String patient);

    void delete(Appointment appointment);
}
