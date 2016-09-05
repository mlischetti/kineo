package com.kineo.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by mlischetti on 12/8/15.
 */
@Entity
@Table(name = "MEDICAL_COMPANY")
public class MedicalInsuranceCompany extends BaseEntity {

    public static final String ENTITY = "MEDICAL_COMPANY";

    @Column(name = "DELETED", nullable = false)
    private boolean deleted = false;

    @Column(name = "NAME", nullable = false)
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
