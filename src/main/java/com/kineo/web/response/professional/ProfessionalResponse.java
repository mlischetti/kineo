package com.kineo.web.response.professional;

/**
 * Created by mlischetti on 12/7/15.
 */
public class ProfessionalResponse {

    private Long id;

    public ProfessionalResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
