package com.kineo.repository;

import com.kineo.model.Appointment;

import java.util.List;

/**
 * Created by Mariano on 1/24/2016.
 */
public interface AppointmentRepository extends BaseRepository<Appointment> {

    List<Appointment> getAppointmentsToSync();
}
