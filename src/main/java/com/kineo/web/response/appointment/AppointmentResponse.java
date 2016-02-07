package com.kineo.web.response.appointment;

/**
 * Created by Mariano on 1/24/2016.
 */
public class AppointmentResponse {
    private Long id;

    public AppointmentResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
