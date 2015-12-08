package com.kinesio.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by mlischetti on 11/27/15.
 */
@Entity
@Table(name = "Patient")
public class Patient extends BaseEntity {

    public static final String ENTITY = "Patient";

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @OneToOne
    @JoinColumn(name = "medical_insurance_id")
    private MedicalInsurance medicalInsurance;

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
