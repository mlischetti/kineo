package com.kineo.web.controller;

import com.kineo.model.Doctor;
import com.kineo.model.DocumentType;
import com.kineo.service.internal.DoctorService;
import com.kineo.util.MediaType;
import com.kineo.web.dto.DoctorDto;
import com.kineo.web.exception.EntityNotFoundException;
import com.kineo.web.exception.ValidationException;
import com.kineo.web.request.doctor.DoctorRequest;
import com.kineo.web.response.PaginationResponse;
import com.kineo.web.response.Paging;
import com.kineo.web.response.doctor.DoctorResponse;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlischetti on 11/29/15.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    private static final Integer FIRST_RESULT = 0;
    private static final Integer MAX_RESULT = 100;

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
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

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public PaginationResponse<DoctorDto> getDoctors(@RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "offset", required = false) Integer offset) {
        LOGGER.debug("Retrieving doctors...");
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
        LOGGER.debug("Retrieve doctors from:{}, limit:{}", fistResult, maxResult);
        PaginationResponse<DoctorDto> response = new PaginationResponse<>();
        List<Doctor> doctors = doctorService.find(fistResult, maxResult);
        response.setItems(doctors.stream().map((Doctor doctor) -> new DoctorDto(doctor)).collect(Collectors.toList()));
        Paging paging = new Paging();
        paging.setLimit(maxResult);
        paging.setOffset(fistResult);
        paging.setTotal(doctorService.count());
        response.setPaging(paging);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
    public DoctorResponse createDoctor(@Valid @RequestBody DoctorRequest doctorRequest) {
        LOGGER.debug("Creating doctor...");
        Doctor doctor = new Doctor();
        saveOrUpdate(doctor, doctorRequest);
        LOGGER.debug("Created doctor: {}", doctor.getId());
        return new DoctorResponse(doctor.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
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
        saveOrUpdate(doctor, doctorRequest);
        LOGGER.debug("Updated doctor: {}", id);
        return new DoctorResponse(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDoctor(@PathVariable("id") Long id) {
        LOGGER.debug("Deleting doctor: {}", id);
        Doctor doctor = doctorService.findById(id);
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Doctor.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        doctorService.delete(doctor);
        LOGGER.debug("Deleted doctor: {}", id);
    }

    private void saveOrUpdate(Doctor doctor, DoctorRequest doctorRequest) {
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor.setEmail(doctorRequest.getEmail());
        doctor.setPhone(doctorRequest.getPhone());
        doctor.setDocumentType(DocumentType.valueOf(doctorRequest.getDocumentType()));
        doctor.setDocumentNumber(doctorRequest.getDocumentNumber());
        doctorService.save(doctor);
    }
}
