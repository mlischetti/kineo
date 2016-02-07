package com.kineo.process;

import com.kineo.model.Appointment;
import com.kineo.model.CalendarEventStatus;
import com.kineo.service.external.CalendarService;
import com.kineo.service.internal.AppointmentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Mariano on 1/31/2016.
 */

@Component("calendar-sync-event")
@Scope(value = "prototype")
public class CalendarSyncEvent implements Process {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarSyncEvent.class);

    private CalendarService calendarService;
    private AppointmentService appointmentService;
    private Appointment appointment;

    @Autowired
    public CalendarSyncEvent(CalendarService calendarService, AppointmentService appointmentService) {
        this.calendarService = calendarService;
        this.appointmentService = appointmentService;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public void run() {
        if (appointment != null && CalendarEventStatus.PENDING.equals(appointment.getEvent().getStatus())) {
            LOGGER.debug("Sync appointment: {}, status: {}", appointment.getId(), appointment.getStatus());
            boolean sync = false;
            switch (appointment.getStatus()) {
                case CONFIRM: {
                    String eventId = calendarService.submit(appointment);
                    if (StringUtils.isNotBlank(eventId)) {
                        appointment.getEvent().setEventId(eventId);
                    }
                    sync = true;
                    break;
                }
                case DELETE: {
                    boolean delete = calendarService.delete(appointment);
                    if (delete) {
                        sync = true;
                    }
                    break;
                }
            }
            if (sync) {
                LOGGER.debug("Appointment: {} synchronized", appointment.getId());
                appointment.getEvent().setStatus(CalendarEventStatus.SYNC);
                appointmentService.save(appointment);
            } else {
                LOGGER.error("Fail on sync appointment: {}", appointment.getId());
            }
        }
    }
}
