package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mlischetti on 12/8/15.
 */
@Entity
@Table(name = "Medical_Company")
public class MedicalInsuranceCompany extends BaseEntity {

    public static final String ENTITY = "Medical_Company";

    private boolean deleted = false;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
