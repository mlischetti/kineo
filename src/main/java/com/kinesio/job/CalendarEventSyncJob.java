package com.kinesio.job;

import com.kinesio.model.Appointment;
import com.kinesio.process.ProcessFactory;
import com.kinesio.service.internal.AppointmentService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Mariano on 1/31/2016.
 */
@Component("calender-event-sync-job")
public class CalendarEventSyncJob extends BaseJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarEventSyncJob.class);

    private AppointmentService appointmentService;
    private ProcessFactory processFactory;
    private AsyncTaskExecutor processExecutor;

    @Value("${kinesio.calendar.calendar-event-sync-job.enabled}")
    private boolean enabled;

    @Autowired
    public CalendarEventSyncJob(AppointmentService appointmentService, ProcessFactory processFactory, AsyncTaskExecutor processExecutor) {
        this.appointmentService = appointmentService;
        this.processFactory = processFactory;
        this.processExecutor = processExecutor;
    }

    @Override
    void work() {
        if (enabled) {
            List<Future> results = new ArrayList<>();
            List<Appointment> appointments = appointmentService.getAppointmentsToSync();
            if (CollectionUtils.isNotEmpty(appointments)) {
                appointments.forEach(appointment -> {
                    com.kinesio.process.Process process = processFactory.create(appointment);
                    if (process != null) {
                        results.add(processExecutor.submit(process));
                    } else {
                        LOGGER.error("Process Factory could not create process for appointment: {}", appointment.getId());
                    }
                });
            }

            // waits till all process completion
            results.forEach(result -> {
                try {
                    // waits till completion
                    result.get();
                } catch (InterruptedException ie) {
                    LOGGER.info("Process Interrupted", ie);
                } catch (ExecutionException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });
        }
    }
}