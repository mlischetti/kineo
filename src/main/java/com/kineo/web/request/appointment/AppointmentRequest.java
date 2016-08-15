package com.kineo.web.request.appointment;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentRequest {

    @NotBlank
    private String summary;

    private DateTime startTime;

    private Long doctorId;

    private Long patientId;

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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
