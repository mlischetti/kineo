package com.kinesio.service.external;

import com.kinesio.model.Appointment;

/**
 * Created by mlischetti on 12/10/15.
 */
public interface CalendarService {
    String submit(Appointment appointment);

    boolean delete(Appointment appointment);
}
