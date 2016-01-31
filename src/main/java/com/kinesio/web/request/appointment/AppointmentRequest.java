package com.kinesio.web.request.appointment;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentRequest {

    @NotBlank
    private String summary;

    private Date startTime;

    private Date endTime;

    private Long doctorId;

    private Long patientId;

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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
