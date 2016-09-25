package com.kineo.web.response.appointment;

import com.kineo.model.AppointmentStatus;

/**
 * Created by mlischetti on 16/09/16.
 */
public class Criteria {

    private AppointmentStatus status;
    private String since;
    private String until;
    private String professional;
    private String patient;

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getUntil() {
        return until;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}
