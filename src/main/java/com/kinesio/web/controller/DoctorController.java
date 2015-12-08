package com.kinesio.web.controller;

import com.kinesio.model.Doctor;
import com.kinesio.service.internal.DoctorService;
import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.request.doctor.NewDoctorRequest;
import com.kinesio.web.request.doctor.UpdateDoctorRequest;
import com.kinesio.web.response.doctor.GetDoctorResponse;
import com.kinesio.web.response.doctor.NewDoctorResponse;
import com.kinesio.web.response.doctor.UpdateDoctorResponse;
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

    @RequestMapping(value = "/doctors/{doctor_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GetDoctorResponse getDoctor(@PathVariable("doctor_id") Long doctorId) {

        LOGGER.debug("Retrieving data for doctor: {}", doctorId);
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", doctorId);
            EntityNotFoundException exception = new EntityNotFoundException("doctor");
            exception.setSearchMessage("doctor_id = " + doctorId);
            throw exception;
        }
        return new GetDoctorResponse(doctor);
    }

    @RequestMapping(value = "/doctors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public NewDoctorResponse newDoctor(@Valid @RequestBody NewDoctorRequest doctorRequest) {
        LOGGER.debug("Creating doctor...");
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor = doctorService.save(doctor);
        LOGGER.debug("Created doctor: {}", doctor.getId());
        return new NewDoctorResponse(doctor.getId());
    }

    @RequestMapping(value = "/doctors/{doctor_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateDoctorResponse updateDoctor(@PathVariable("doctor_id") Long doctorId,
                                             @Valid @RequestBody UpdateDoctorRequest doctorRequest) {
        LOGGER.debug("Updating doctor: {}", doctorId);
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", doctorId);
            EntityNotFoundException exception = new EntityNotFoundException("doctor");
            exception.setSearchMessage("doctor_id = " + doctorId);
            throw exception;
        }
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctorService.save(doctor);

        LOGGER.debug("Updated doctor: {}", doctorId);
        return new UpdateDoctorResponse(doctor.getId());
    }

}
