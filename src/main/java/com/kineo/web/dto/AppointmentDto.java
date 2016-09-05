package com.kineo.web.dto;

import com.kineo.model.Appointment;
import com.kineo.model.AppointmentStatus;
import org.joda.time.DateTime;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentDto {
    private Long id;
    private AppointmentStatus status;
    private CalendarEventDto event;
    private String summary;
    private DateTime startTime;
    private DateTime endTime;
    private Long professionalId;
    private Long patientId;

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getId();
        this.status = appointment.getStatus();
        this.event = new CalendarEventDto(appointment.getEvent());
        this.summary = appointment.getSummary();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
        this.professionalId = appointment.getProfessional().getId();
        this.patientId = appointment.getPatient().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public CalendarEventDto getEvent() {
        return event;
    }

    public void setEvent(CalendarEventDto event) {
        this.event = event;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public Long getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(Long professionalId) {
        this.professionalId = professionalId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
