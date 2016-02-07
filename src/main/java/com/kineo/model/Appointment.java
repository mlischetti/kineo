package com.kineo.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mlischetti on 11/27/15.
 */
@Entity
@Table(name = "Appointment")
public class Appointment extends BaseEntity {

    public static final String ENTITY = "Appointment";

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.CONFIRM;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private CalendarEvent event = new CalendarEvent();

    private String summary;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        if(this.status != status) {
            getEvent().setStatus(CalendarEventStatus.PENDING);
        }
        this.status = status;
    }

    public CalendarEvent getEvent() {
        return event;
    }

    public void setEvent(CalendarEvent event) {
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
