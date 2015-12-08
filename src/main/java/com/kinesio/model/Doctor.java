package com.kinesio.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mlischetti on 11/27/15.
 */

@Entity
@Table(name = "Doctor")
public class Doctor extends BaseEntity {

    public static final String ENTITY = "Doctor";

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
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
