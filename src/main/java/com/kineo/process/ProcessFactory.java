package com.kineo.process;

import com.kineo.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Mariano on 1/31/2016.
 */
@Component
public class ProcessFactory {

    private ApplicationContext context;

    @Autowired
    public ProcessFactory(ApplicationContext context) {
        this.context = context;
    }

    @SuppressWarnings("rawtypes")
    public Process create(Appointment appointment) {
        CalendarSyncEvent process = context.getBean("calendar-sync-event", CalendarSyncEvent.class);
        process.setAppointment(appointment);
        return process;
    }
}

