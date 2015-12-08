package com.kinesio.web.request.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by mlischetti on 11/29/15.
 */
public class NewDoctorRequest {

    @NotEmpty
    @JsonProperty(value = "first_name")
    private String firstName;

    @NotEmpty
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
