package com.kinesio.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kinesio.model.Doctor;

/**
 * Created by mlischetti on 12/7/15.
 */
public class DoctorDto {

    private Long id;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "last_name")
    private String lastName;

    public DoctorDto(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
