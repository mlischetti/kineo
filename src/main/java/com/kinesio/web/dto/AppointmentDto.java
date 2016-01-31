package com.kinesio.web.dto;

import com.kinesio.model.Appointment;
import com.kinesio.model.AppointmentStatus;

import java.util.Date;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentDto {
    private Long id;
    private AppointmentStatus status;
    private CalendarEventDto event;
    private String summary;
    private Date startTime;
    private Date endTime;
    private Long doctorId;

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getId();
        this.status = appointment.getStatus();
        this.event = new CalendarEventDto(appointment.getEvent());
        this.summary = appointment.getSummary();
        this.startTime = appointment.getStartTime();
        this.endTime = appointment.getEndTime();
        this.doctorId = appointment.getDoctor().getId();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
