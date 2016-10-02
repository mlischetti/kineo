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
import com.kineo.util.date.DateUtils;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class GoogleCalendarService implements CalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCalendarService.class);

    //    private static final String TEMPLATE = "appointment-message.ftl";
//    private static final String SUMMARY = "Kineo: Confirmacion de turno solicitado";
    private static final String TEMPLATE = "appointment-to-professional-description.ftl";
    private static final String LOCATION = "Av. Bernardo de Yrigoyen 220 Chivilcoy";
    private static final String TELEPHONE = "02346 42-0640";
    private static final TimeZone APPOINTMENT_TZ = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");

    @Value("${kineo.calendar.calendarId}")
    private String calendarId;

    @Value("${kineo.calendar.serviceAccountId}")
    private String serviceAccountId;

    @Value("${kineo.calendar.keyFilePath}")
    private String keyFilePath;

    @Value("${kineo.calendar.applicationName}")
    private String applicationName;

    private com.google.api.services.calendar.Calendar calendarService = null;

    @Autowired
    protected FreeMarkerConfig config;

    @PostConstruct
    public void setup() {
        GoogleCredential credentials;
        HttpTransport httpTransport;
        File keyFile;
        LOGGER.info("Creating google calendar service instance");
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
        LOGGER.info("Created google calendar service instance");
    }

    @Override
    public String submit(Appointment appointment) {
        LOGGER.info("Submit appointment: {}", appointment.getId());
        String eventId;
        if (StringUtils.isNotBlank(appointment.getEvent().getEventId())) {
            updateEvent(appointment);
            eventId = appointment.getEvent().getEventId();
        } else {
            eventId = insertEvent(appointment);
        }
        LOGGER.info("Appointment: {} was submitted, event: {}.", appointment.getId(), eventId);
        return eventId;
    }

    private String insertEvent(Appointment appointment) {
        String eventId = null;
        try {
            LOGGER.info("Insert new event based on appointment: {} into calendar", appointment.getId());
            Event event = calendarService.events().insert(calendarId, build(appointment)).setSendNotifications(true).execute();
            eventId = event.getId();
            LOGGER.info("Inserted event: {} based on appointment: {} into calendar", eventId, appointment.getId());
        } catch (IOException e) {
            LOGGER.error("Could not insert event based on appointment: {}", appointment.getId(), e);
        }
        return eventId;
    }

    private void updateEvent(Appointment appointment) {
        String eventId = appointment.getEvent().getEventId();
        try {
            LOGGER.info("Updating event: {} based on appointment: {}", eventId, appointment.getId());
            calendarService.events().update(calendarId, eventId, build(appointment)).setSendNotifications(true).execute();
            LOGGER.info("Updated event: {} based on appointment: {}", eventId, appointment.getId());
        } catch (IOException e) {
            LOGGER.error("Could not update event:{}", eventId, e);
        }
    }

    private Event build(Appointment appointment) {
        Event event = new Event().setSummary(getSummary(appointment));
        event.setLocation(LOCATION);
        String description = getDescription(appointment);
        if (StringUtils.isNotBlank(description)) {
            event.setDescription(description);
        }

        //Guests access
        event.setGuestsCanModify(false);
        event.setGuestsCanInviteOthers(false);
        event.setGuestsCanSeeOtherGuests(false);

        //Attendees
        List<EventAttendee> attendees = new ArrayList<>();
        if (StringUtils.isNotBlank(appointment.getProfessional().getEmail())) {
            attendees.add(new EventAttendee().setEmail(appointment.getProfessional().getEmail()));
        }

        /*
        if (StringUtils.isNotBlank(appointment.getPatient().getEmail())) {
            attendees.add(new EventAttendee().setEmail(appointment.getPatient().getEmail()));
        }
        */
        event.setAttendees(attendees);

        //Dates
        DateTime start = new DateTime(appointment.getStartTime().toDate(), APPOINTMENT_TZ);
        event.setStart(new EventDateTime().setDateTime(start));

        DateTime end = new DateTime(appointment.getEndTime().toDate(), APPOINTMENT_TZ);
        event.setEnd(new EventDateTime().setDateTime(end));
        return event;
    }

    private String getSummary(Appointment appointment) {
        StringBuilder builder = new StringBuilder(300);
        builder.append(appointment.getService());
        builder.append(": ");
        builder.append(appointment.getProfessional().getFullName());
        builder.append(" - ");
        builder.append(appointment.getPatient().getFullName());
        return builder.toString();
    }

    private String getDescription(Appointment appointment) {
        String description = null;
        try {
            Template template = config.getConfiguration().getTemplate(TEMPLATE, "UTF-8");
            String appointmentDateTime = DateUtils.printAsArgDateTime(appointment.getStartTime());

            Map<String, String> data = new HashMap<>();
            data.put("professionalCategory", appointment.getProfessional().getCategory().getShortDescription());
            data.put("professionalFullname", appointment.getProfessional().getFullName());
            data.put("appointmentDateTime", appointmentDateTime);
            data.put("patientFullname", appointment.getPatient().getFullName());
            data.put("service", appointment.getService());
            description = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        } catch (Throwable t) {
            LOGGER.error("Fail on getDescription.", t);
        }

        return description;
    }

//    private String getDescription(Appointment appointment) {
//        String description = null;
//        try {
//            Template template = config.getConfiguration().getTemplate(TEMPLATE, "UTF-8");
//            String[] appointmentDateTime = DateUtils.printAsArgDateTime(appointment.getStartTime()).split(" ");
//
//            Map<String, String> data = new HashMap<>();
//            data.put("patientFullname", appointment.getPatient().getFullName().toUpperCase());
//            data.put("appointmentDate", appointmentDateTime[0]);
//            data.put("appointmentTime", appointmentDateTime[1]);
//            data.put("professionalCategory", appointment.getProfessional().getCategory().getShortDescription());
//            data.put("professionalFullname", appointment.getProfessional().getFullName().toUpperCase());
//            data.put("service", appointment.getService());
//            data.put("location", LOCATION);
//            data.put("telephone", TELEPHONE);
//            description = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
//        } catch (Throwable t) {
//            LOGGER.error("Fail on getDescription.", t);
//        }
//
//        return description;
//    }

    @Override
    public boolean delete(Appointment appointment) {
        String eventId = appointment.getEvent().getEventId();
        if (StringUtils.isNotBlank(eventId)) {
            try {
                LOGGER.info("Deleting event: {} based on appointment: {}", eventId, appointment.getId());
                calendarService.events().delete(calendarId, eventId).setSendNotifications(true).execute();
                LOGGER.info("Deleted event: {} based on appointment: {}", eventId, appointment.getId());
                return true;
            } catch (IOException e) {
                LOGGER.error("Could not delete event: {}", eventId, e);
            }
        }
        return false;
    }
}
