package com.kineo.web.request.appointment;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentRequest {

    @NotBlank
    private String service;

    private DateTime startTime;

    private Long professionalId;

    private Long patientId;

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
