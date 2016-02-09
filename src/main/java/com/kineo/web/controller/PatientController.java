package com.kineo.web.controller;

import com.kineo.model.MedicalInsurancePlan;
import com.kineo.model.Patient;
import com.kineo.service.internal.MedicalInsuranceService;
import com.kineo.service.internal.PatientService;
import com.kineo.util.MediaType;
import com.kineo.web.dto.PatientDto;
import com.kineo.web.exception.EntityNotFoundException;
import com.kineo.web.exception.ValidationException;
import com.kineo.web.request.patient.PatientRequest;
import com.kineo.web.response.PaginationResponse;
import com.kineo.web.response.Paging;
import com.kineo.web.response.patient.PatientResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by mlischetti on 12/7/15.
 */
@RestController
@RequestMapping("/api")
public class PatientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    private static final Integer FIRST_RESULT = 0;
    private static final Integer MAX_RESULT = 100;

    private PatientService patientService;
    private MedicalInsuranceService medicalInsuranceService;

    @Autowired
    public PatientController(PatientService patientService, MedicalInsuranceService medicalInsuranceService) {
        this.patientService = patientService;
        this.medicalInsuranceService = medicalInsuranceService;
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
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

    @RequestMapping(value = "/patients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public PaginationResponse<PatientDto> getPatients(@RequestParam(value = "limit", required = false) Integer limit,
                                                      @RequestParam(value = "offset", required = false) Integer offset) {
        LOGGER.debug("Retrieving patients ...");
        int fistResult = FIRST_RESULT;
        if (offset != null) {
            fistResult = offset;
        }
        int maxResult = MAX_RESULT;
        if (limit != null) {
            if (limit > MAX_RESULT) {
                ValidationException exception = new ValidationException();
                exception.errorField("limit", "Could not be greater than " + MAX_RESULT);
                throw exception;
            }
            maxResult = limit;
        }
        LOGGER.debug("Retrieve medical insurance patients from:{}, limit:{}", fistResult, maxResult);
        PaginationResponse<PatientDto> response = new PaginationResponse<>();
        List<Patient> patients = patientService.find(fistResult, maxResult);
        if (CollectionUtils.isNotEmpty(patients)) {
            for (Patient patient : patients) {
                response.addItem(new PatientDto(patient));
            }
        }
        Paging paging = new Paging();
        paging.setLimit(maxResult);
        paging.setOffset(fistResult);
        paging.setTotal(patientService.count());
        response.setPaging(paging);
        return response;
    }

    @RequestMapping(value = "/patients", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
    public PatientResponse createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        LOGGER.debug("Creating patient...");
        MedicalInsurancePlan medicalInsurancePlan = null;
        if (patientRequest.getMedicalInsurancePlanId() != null) {
            medicalInsurancePlan = medicalInsuranceService.findPlanById(patientRequest.getMedicalInsurancePlanId());
            if (medicalInsurancePlan == null) {
                LOGGER.debug("Could not found medial insurance plan: {}", patientRequest.getMedicalInsurancePlanId());
                EntityNotFoundException exception = new EntityNotFoundException(MedicalInsurancePlan.ENTITY);
                exception.setSearchMessage("id = " + patientRequest.getMedicalInsurancePlanId());
                throw exception;
            }
        }
        Patient patient = new Patient();
        saveOrUpdate(patient, medicalInsurancePlan, patientRequest);
        patientService.save(patient);
        LOGGER.debug("Created patient: {}", patient.getId());
        return new PatientResponse(patient.getId());
    }

    @RequestMapping(value = "/patients/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON_UTF_8)
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
        MedicalInsurancePlan medicalInsurancePlan = null;
        if (patientRequest.getMedicalInsurancePlanId() != null) {
            medicalInsurancePlan = medicalInsuranceService.findPlanById(patientRequest.getMedicalInsurancePlanId());
            if (medicalInsurancePlan == null) {
                LOGGER.debug("Could not found medial insurance plan: {}", patientRequest.getMedicalInsurancePlanId());
                EntityNotFoundException exception = new EntityNotFoundException(MedicalInsurancePlan.ENTITY);
                exception.setSearchMessage("id = " + patientRequest.getMedicalInsurancePlanId());
                throw exception;
            }
        }

        saveOrUpdate(patient, medicalInsurancePlan, patientRequest);
        LOGGER.debug("Updated patient: {}", id);
        return new PatientResponse(id);
    }

    private void saveOrUpdate(Patient patient, MedicalInsurancePlan medicalInsurancePlan, PatientRequest patientRequest) {
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setEmail(patientRequest.getEmail());
        patient.setPhone(patientRequest.getPhone());
        patient.setAffiliateId(patientRequest.getAffiliateId());
        patient.setMedicalInsurancePlan(medicalInsurancePlan);
        patientService.save(patient);
    }
}
