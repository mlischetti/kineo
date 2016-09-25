package com.kineo.repository;

import com.kineo.model.Appointment;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
public interface AppointmentRepository extends BaseRepository<Appointment> {

    List<Appointment> find(DateTime since, DateTime until, String professional, String patient);

    List<Appointment> getAppointmentsToSync();
}
