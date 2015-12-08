package com.kinesio.web.controller;

import com.kinesio.model.Doctor;
import com.kinesio.service.internal.DoctorService;
import com.kinesio.web.dto.DoctorDto;
import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.request.doctor.DoctorRequest;
import com.kinesio.web.response.doctor.DoctorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by mlischetti on 11/29/15.
 */
@RestController
@RequestMapping("/api")
public class DoctorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(value = "/doctors/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DoctorDto getDoctor(@PathVariable("id") Long id) {

        LOGGER.debug("Retrieving doctor: {}", id);
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Doctor.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new DoctorDto(doctor);
    }

    @RequestMapping(value = "/doctors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DoctorResponse createDoctor(@Valid @RequestBody DoctorRequest doctorRequest) {
        LOGGER.debug("Creating doctor...");
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctorService.save(doctor);
        LOGGER.debug("Created doctor: {}", doctor.getId());
        return new DoctorResponse(doctor.getId());
    }

    @RequestMapping(value = "/doctors/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DoctorResponse updateDoctor(@PathVariable("id") Long id,
                                       @Valid @RequestBody DoctorRequest doctorRequest) {
        LOGGER.debug("Updating doctor: {}", id);
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Doctor.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctorService.save(doctor);

        LOGGER.debug("Updated doctor: {}", id);
        return new DoctorResponse(id);
    }

}
