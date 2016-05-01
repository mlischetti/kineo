package com.kineo.web.controller;

import com.kineo.model.Appointment;
import com.kineo.model.Doctor;
import com.kineo.model.Patient;
import com.kineo.service.internal.AppointmentService;
import com.kineo.service.internal.DoctorService;
import com.kineo.service.internal.PatientService;
import com.kineo.util.MediaType;
import com.kineo.web.dto.AppointmentDto;
import com.kineo.web.exception.EntityNotFoundException;
import com.kineo.web.request.appointment.AppointmentRequest;
import com.kineo.web.response.appointment.AppointmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by mlischetti on 12/7/15.
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
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
        LOGGER.debug("Creating patient...");

        Doctor doctor = doctorService.findById(appointmentRequest.getDoctorId());
        if (doctor == null) {
            LOGGER.debug("Could not found patient: {}", appointmentRequest.getDoctorId());
            EntityNotFoundException exception = new EntityNotFoundException(Doctor.ENTITY);
            exception.setSearchMessage("id = " + appointmentRequest.getDoctorId());
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
        appointment.setSummary(appointmentRequest.getSummary());
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentService.save(appointment);
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

        Doctor doctor = doctorService.findById(appointmentRequest.getDoctorId());
        if (doctor == null) {
            LOGGER.debug("Could not found doctor: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Doctor.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }

        Patient patient = patientService.findById(appointmentRequest.getPatientId());
        if (patient == null) {
            LOGGER.debug("Could not found patient: {}", id);
            EntityNotFoundException exception = new EntityNotFoundException(Patient.ENTITY);
            exception.setSearchMessage("id = " + id);
            throw exception;
        }
        saveOrUpdate(appointment, doctor, patient, appointmentRequest);
        LOGGER.debug("Updated patient: {}", id);
        return new AppointmentResponse(id);
    }

    private void saveOrUpdate(Appointment appointment, Doctor doctor, Patient patient, AppointmentRequest appointmentRequest) {
        appointment.setSummary(appointmentRequest.getSummary());
        appointment.setStartTime(appointmentRequest.getStartTime());
        appointment.setEndTime(appointmentRequest.getEndTime());

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentService.save(appointment);
        LOGGER.debug("Created appointment: {}", appointment.getId());
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
