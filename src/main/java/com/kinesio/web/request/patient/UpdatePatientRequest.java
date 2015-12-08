package com.kinesio.web.request.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by mlischetti on 12/7/15.
 */
public class UpdatePatientRequest {

    @NotBlank
    @JsonProperty(value = "first_name")
    private String firstName;

    @NotBlank
    @JsonProperty(value = "last_name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
