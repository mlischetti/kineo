package com.kineo.service.external;

import com.kineo.model.Appointment;

/**
 * Created by mlischetti on 12/10/15.
 */
public interface CalendarService {
    String submit(Appointment appointment);

    boolean delete(Appointment appointment);
}
