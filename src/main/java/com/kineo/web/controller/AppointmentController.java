package com.kineo.web.controller;

import com.kineo.model.Appointment;
import com.kineo.model.Patient;
import com.kineo.model.Professional;
import com.kineo.service.internal.AppointmentService;
import com.kineo.service.internal.PatientService;
import com.kineo.service.internal.ProfessionalService;
import com.kineo.util.MediaType;
import com.kineo.util.date.DateUtils;
import com.kineo.web.dto.AppointmentDto;
import com.kineo.web.exception.EntityNotFoundException;
import com.kineo.web.request.appointment.AppointmentRequest;
import com.kineo.web.response.appointment.AppointmentResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mlischetti on 12/7/15.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    private AppointmentService appointmentService;
    private ProfessionalService professionalService;
    private PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ProfessionalService professionalService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.professionalService = professionalService;
        this.patientService = patientService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public AppointmentDto getAppointment(@PathVariable(value = "id") Long id) {
        LOGGER.debug("Retrieving appointment: {}", id);
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            LOGGER.debug("Could not found appointment: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Appointment.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        return new AppointmentDto(appointment);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_UTF_8)
    public AppointmentResponse createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        LOGGER.debug("Creating appointment...");

        Professional professional = professionalService.findById(appointmentRequest.getProfessionalId());
        if (professional == null) {
            LOGGER.debug("Could not found professional: {}", appointmentRequest.getProfessionalId());
            EntityNotFoundException exception = new EntityNotFoundException(Professional.ENTITY);
            exception.setSearchMessage("id = " + appointmentRequest.getProfessionalId());
            throw exception;
        }

        Patient patient = patientService.findById(appointmentRequest.getPatientId());
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", appointmentRequest.getPatientId());
            EntityNotFoundException exception = new EntityNotFoundException(Patient.ENTITY);
            exception.setSearchMessage("id = " + appointmentRequest.getPatientId());
            throw exception;
        }

        Appointment appointment = new Appointment();
        saveOrUpdate(appointment, professional, patient, appointmentRequest);
        LOGGER.debug("Created appointment: {}", appointment.getId());
        return new AppointmentResponse(appointment.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON_UTF_8)
    public AppointmentResponse updateAppointment(@PathVariable(value = "id") Long id,
                                                 @Valid @RequestBody AppointmentRequest appointmentRequest) {
        LOGGER.debug("Updating appointment: {}", id);
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            LOGGER.debug("Could not found appointment: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Appointment.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }

        Professional professional = professionalService.findById(appointmentRequest.getProfessionalId());
        if (professional == null) {
            LOGGER.debug("Could not found professional: {}", appointmentRequest.getProfessionalId());
            EntityNotFoundException exception = new EntityNotFoundException(Professional.ENTITY);
            exception.setSearchMessage("id = " + appointmentRequest.getProfessionalId());
            throw exception;
        }

        Patient patient = patientService.findById(appointmentRequest.getPatientId());
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", appointmentRequest.getPatientId());
            EntityNotFoundException exception = new EntityNotFoundException(Patient.ENTITY);
            exception.setSearchMessage("id = " + appointmentRequest.getPatientId());
            throw exception;
        }
        saveOrUpdate(appointment, professional, patient, appointmentRequest);
        LOGGER.debug("Updated patient: {}", id);
        return new AppointmentResponse(id);
    }

    private void saveOrUpdate(Appointment appointment, Professional professional, Patient patient, AppointmentRequest appointmentRequest) {
        appointment.setSummary(appointmentRequest.getSummary());
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setProfessional(professional);
        appointment.setPatient(patient);
        appointmentService.save(appointment);
        LOGGER.debug("Created appointment: {}", appointment.getId());
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF_8)
    public List<AppointmentDto> getAppointments(@RequestParam(value = "since", required = false) String since,
                                                @RequestParam(value = "until", required = false) String until,
                                                @RequestParam(value = "professional", required = false) String professional,
                                                @RequestParam(value = "patient", required = false) String patient) {

        DateTime sinceDateTime = DateUtils.parseAsSimpleDateTime(since);
        DateTime untilDateTime = DateUtils.parseAsSimpleDateTime(until);

        List<Appointment> appointments = appointmentService.find(sinceDateTime, untilDateTime, professional, patient);
        List<AppointmentDto> response = appointments.stream().map((Appointment appointment) -> new AppointmentDto(appointment)).collect(Collectors.toList());
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAppointment(@PathVariable(value = "id") Long id) {
        LOGGER.debug("Deleting appointment: {}", id);
        Appointment appointment = appointmentService.findById(id);
        if (appointment == null) {
            LOGGER.debug("Could not found appointment: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Appointment.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        appointmentService.delete(appointment);
        LOGGER.debug("Deleted appointment: {}", id);
    }
}
