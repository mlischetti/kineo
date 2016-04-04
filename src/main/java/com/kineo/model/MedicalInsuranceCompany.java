package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mlischetti on 12/8/15.
 */
@Entity
@Table(name = "Medical_Company")
public class MedicalInsuranceCompany extends BaseEntity {

    public static final String ENTITY = "Medical_Company";

    @NotEmpty
    private String name;

    private boolean deleted = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
