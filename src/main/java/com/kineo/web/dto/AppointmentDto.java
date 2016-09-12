package com.kineo.web.dto;

import com.kineo.model.Appointment;
import com.kineo.model.AppointmentStatus;
import com.kineo.util.date.DateUtils;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentDto {
    private Long id;
    private AppointmentStatus status;
    private CalendarEventDto event;
    private String summary;
    private String startTime;
    private String endTime;
    private ProfessionalDto professional;
    private PatientDto patient;

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getId();
        this.status = appointment.getStatus();
        this.event = new CalendarEventDto(appointment.getEvent());
        this.summary = appointment.getSummary();
        this.startTime = DateUtils.printDateTime(appointment.getStartTime());
        this.endTime = DateUtils.printDateTime(appointment.getEndTime());
        this.professional = new ProfessionalDto(appointment.getProfessional());
        this.patient = new PatientDto(appointment.getPatient());
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ProfessionalDto getProfessional() {
        return professional;
    }

    public void setProfessional(ProfessionalDto professional) {
        this.professional = professional;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }
}
