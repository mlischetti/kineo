package com.kinesio.web.response.doctor;

/**
 * Created by mlischetti on 12/7/15.
 */
public class UpdateDoctorResponse {

    private Long id;

    public UpdateDoctorResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
