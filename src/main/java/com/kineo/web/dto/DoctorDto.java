package com.kineo.web.dto;

import com.kineo.model.Doctor;
import com.kineo.util.date.DateUtils;

import java.util.Date;

/**
 * Created by mlischetti on 12/7/15.
 */
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String documentType;
    private String documentNumber;

    public DoctorDto(Doctor doctor) {
        this.id = doctor.getId();
        this.firstName = doctor.getFirstName();
        this.lastName = doctor.getLastName();
        this.dateOfBirth = DateUtils.printAsSimpleDate(doctor.getDateOfBirth());
        this.email = doctor.getEmail();
        this.phone = doctor.getPhone();
        if(doctor.getDocumentType() != null) {
            this.documentType = doctor.getDocumentType().name();
        }
        this.documentNumber = doctor.getDocumentNumber();
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
