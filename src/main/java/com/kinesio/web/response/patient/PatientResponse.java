package com.kinesio.web.response.patient;

/**
 * Created by mlischetti on 12/7/15.
 */
public class PatientResponse {

    private Long id;

    public PatientResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
