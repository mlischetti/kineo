package com.kinesio.web.controller;

import com.kinesio.model.Patient;
import com.kinesio.service.internal.PatientService;
import com.kinesio.web.exception.EntityNotFoundException;
import com.kinesio.web.request.patient.NewPatientRequest;
import com.kinesio.web.request.patient.UpdatePatientRequest;
import com.kinesio.web.response.patient.GetPatientResponse;
import com.kinesio.web.response.patient.NewPatientResponse;
import com.kinesio.web.response.patient.UpdatePatientResponse;
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

    @RequestMapping(value = "/patients/{patient_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GetPatientResponse getPatient(@PathVariable(value = "patient_id") Long patientId) {
        LOGGER.debug("Retrieving patient: {}", patientId);
        GetPatientResponse response = null;
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", patientId);
            EntityNotFoundException exception = new EntityNotFoundException("patient");
            exception.setSearchMessage("patient_id = " + patientId);
            throw exception;
        }
        response = new GetPatientResponse(patient);

        return response;
    }

    @RequestMapping(value = "/patients", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NewPatientResponse newPatient(@Valid @RequestBody NewPatientRequest patientRequest) {
        LOGGER.debug("Creating patient...");
        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient = patientService.save(patient);
        LOGGER.debug("Created patient: {}", patient.getId());
        return new NewPatientResponse(patient.getId());
    }

    @RequestMapping(value = "/patients/{patient_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdatePatientResponse updatePatient(@PathVariable(value = "patient_id") Long patientId,
                                               @Valid @RequestBody UpdatePatientRequest patientRequest) {
        LOGGER.debug("Updating patient: {}", patientId);
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", patientId);
            EntityNotFoundException exception = new EntityNotFoundException("patient");
            exception.setSearchMessage("patient_id = " + patientId);
            throw exception;
        }
        patient = patientService.save(patient);
        LOGGER.debug("Updated patient: {}", patient);
        return new UpdatePatientResponse(patient.getId());
    }
}
