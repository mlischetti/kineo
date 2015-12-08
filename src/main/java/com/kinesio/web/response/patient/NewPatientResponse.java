package com.kinesio.web.response.patient;

/**
 * Created by mlischetti on 12/7/15.
 */
public class NewPatientResponse {

    private Long id;

    public NewPatientResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
