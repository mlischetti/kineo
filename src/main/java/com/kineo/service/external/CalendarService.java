package com.kineo.service.external;

import com.kineo.model.Appointment;

public interface CalendarService {
    String submit(Appointment appointment);

    boolean delete(Appointment appointment);
}
