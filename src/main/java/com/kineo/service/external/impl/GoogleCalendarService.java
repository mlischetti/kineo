package com.kineo.service.external.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.kineo.model.Appointment;
import com.kineo.service.external.CalendarService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by mlischetti on 12/10/15.
 */
@Service
public class GoogleCalendarService implements CalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCalendarService.class);
    private static final String SUMMARY = "Kineo: Confirmacion de turno solicitado";
    private static final String LOCATION = "Av. Bernardo de Yrigoyen 220 Chivilcoy";

    @Value("${kineo.calendar.calendarId}")
    private String calendarId;

    @Value("${kineo.calendar.serviceAccountId}")
    private String serviceAccountId;

    @Value("${kineo.calendar.keyFilePath}")
    private String keyFilePath;

    @Value("${kineo.calendar.applicationName}")
    private String applicationName;

    private com.google.api.services.calendar.Calendar calendarService = null;

    @PostConstruct
    public void setup() {
        GoogleCredential credentials;
        HttpTransport httpTransport;
        File keyFile;
        LOGGER.debug("Creating google calendar service instance");
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            keyFile = new File(keyFilePath);
            credentials = new GoogleCredential.Builder().setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(this.serviceAccountId)
                    .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                    .setServiceAccountPrivateKeyFromP12File(keyFile)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            LOGGER.error("Couldn't create Google Calendar credentials: ", e);
            return;
        }
        calendarService = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credentials).setApplicationName(applicationName).build();
        LOGGER.debug("Created google calendar service instance");
    }

    @Override
    public String submit(Appointment appointment) {
        LOGGER.debug("Submit appointment: {}", appointment.getId());
        String eventId;
        if (StringUtils.isNotBlank(appointment.getEvent().getEventId())) {
            updateEvent(appointment);
            eventId = appointment.getEvent().getEventId();
        } else {
            eventId = insertEvent(appointment);
        }
        LOGGER.debug("Appointment: {} was submitted, event: {}.", appointment.getId(), eventId);
        return eventId;
    }

    private String insertEvent(Appointment appointment) {
        String eventId = null;
        try {
            LOGGER.debug("Insert new event based on appointment: {} into calendar", appointment.getId());
            Event event = calendarService.events().insert(calendarId, build(appointment)).execute();
            eventId = event.getId();
            LOGGER.debug("Inserted event: {} based on appointment: {} into calendar", eventId, appointment.getId());
        } catch (IOException e) {
            LOGGER.error("Could not insert event based on appointment: {}", appointment.getId(), e);
        }
        return eventId;
    }

    private void updateEvent(Appointment appointment) {
        String eventId = appointment.getEvent().getEventId();
        try {
            LOGGER.debug("Updating event: {} based on appointment: {}", eventId, appointment.getId());
            calendarService.events().update(calendarId, eventId, build(appointment)).execute();
            LOGGER.debug("Updated event: {} based on appointment: {}", eventId, appointment.getId());
        } catch (IOException e) {
            LOGGER.error("Could not update event:{}", eventId, e);
        }
    }

    private Event build(Appointment appointment) {
        Event event = new Event().setSummary(SUMMARY).setDescription(getDescription(appointment)).setLocation(LOCATION);

        //Guests access
        event.setGuestsCanModify(false);
        event.setGuestsCanInviteOthers(false);
        event.setGuestsCanSeeOtherGuests(false);

        //Attendees
        List<EventAttendee> attendees = new ArrayList<>();
        if (StringUtils.isNotBlank(appointment.getDoctor().getEmail())) {
            attendees.add(new EventAttendee().setEmail(appointment.getDoctor().getEmail()));
        }
        if (StringUtils.isNotBlank(appointment.getPatient().getEmail())) {
            attendees.add(new EventAttendee().setEmail(appointment.getPatient().getEmail()));
        }
        event.setAttendees(attendees);

        //Dates
        DateTime start = new DateTime(appointment.getStartTime().toDate(), TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        event.setStart(new EventDateTime().setDateTime(start));

        DateTime end = new DateTime(appointment.getEndTime().toDate(), TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    private String getDescription(Appointment appointment) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String[] appointmentTime = dateFormat.format(appointment.getStartTime()).split(" ");
        StringBuilder builder = new StringBuilder(400);
        builder.append("Sr/Sra. " + appointment.getPatient().getFullName().toUpperCase());
        builder.append("\n\n");
        builder.append("Recuerde concurir el dia " + appointmentTime[0] + " a las " + appointmentTime[1] + " hs. a su cita con:");
        builder.append("\n\n");
        builder.append("Dr/a. " + appointment.getDoctor().getFullName().toUpperCase());
        builder.append("\n\n");
        builder.append("Servicio: Kinesiologia");
        builder.append("\n\n");
        builder.append(LOCATION);
        builder.append("\n\n");
        builder.append("Se ruega puntualidad.");
        builder.append("\n\n");
        builder.append("En caso de no poder concurrir, comunicarse al tel: 02346 42-0640");
        return builder.toString();
    }

    @Override
    public boolean delete(Appointment appointment) {
        String eventId = appointment.getEvent().getEventId();
        if (StringUtils.isNotBlank(eventId)) {
            try {
                LOGGER.debug("Deleting event: {} based on appointment: {}", eventId, appointment.getId());
                calendarService.events().delete(calendarId, eventId).setSendNotifications(false).execute();
                LOGGER.debug("Deleted event: {} based on appointment: {}", eventId, appointment.getId());
                return true;
            } catch (IOException e) {
                LOGGER.error("Could not delete event: {}", eventId, e);
            }
        }
        return false;
    }
}
