package com.kineo.web.response.doctor;

/**
 * Created by mlischetti on 12/7/15.
 */
public class DoctorResponse {

    private Long id;

    public DoctorResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
