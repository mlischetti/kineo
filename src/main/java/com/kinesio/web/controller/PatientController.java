package com.kinesio.web.controller;

import com.kinesio.model.Patient;
import com.kinesio.service.internal.PatientService;
import com.kinesio.web.dto.PatientDto;
import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.request.patient.PatientRequest;
import com.kinesio.web.response.patient.PatientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by mlischetti on 12/7/15.
 */
@RestController
@RequestMapping("/api")
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PatientDto getPatient(@PathVariable(value = "id") Long id) {
        LOGGER.debug("Retrieving patient: {}", id);
        Patient patient = patientService.findById(id);
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Patient.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new PatientDto(patient);
    }

    @RequestMapping(value = "/patients", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PatientResponse createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        LOGGER.debug("Creating patient...");
        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patientService.save(patient);
        LOGGER.debug("Created patient: {}", patient.getId());
        return new PatientResponse(patient.getId());
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PatientResponse updatePatient(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody PatientRequest patientRequest) {
        LOGGER.debug("Updating patient: {}", id);
        Patient patient = patientService.findById(id);
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Patient.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        patientService.save(patient);
        LOGGER.debug("Updated patient: {}", id);
        return new PatientResponse(id);
    }
}
