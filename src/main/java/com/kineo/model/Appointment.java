package com.kineo.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment extends BaseEntity {

    public static final String ENTITY = "APPOINTMENT";
    public static final int DEFAULT_DURATION = 30;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppointmentStatus status = AppointmentStatus.CONFIRM;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EVENT_ID")
    private CalendarEvent event = new CalendarEvent();

    @Column(name = "SERVICE")
    private String service;

    @Column(name = "START_TIME")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;

    @Column(name = "DURATION")
    private int duration = DEFAULT_DURATION;

    @ManyToOne
    @JoinColumn(name = "PROFESSIONAL_ID")
    private Professional professional;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public CalendarEvent getEvent() {
        return event;
    }

    public void setEvent(CalendarEvent event) {
        this.event = event;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        if (startTime == null) {
            return null;
        }
        return startTime.plusMinutes(getDuration());
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void markAsConfirmed() {
        this.status = AppointmentStatus.CONFIRM;
        setEventAsPending();
    }

    public void markAsDeleted() {
        this.status = AppointmentStatus.DELETE;
        setEventAsPending();
    }

    private void setEventAsPending() {
        if (this.event != null) {
            this.event.setStatus(CalendarEventStatus.PENDING);
        }
    }
}
