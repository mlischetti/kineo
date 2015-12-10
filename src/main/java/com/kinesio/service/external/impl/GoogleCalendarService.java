package com.kinesio.service.external.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.kinesio.service.external.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/**
 * Created by mlischetti on 12/10/15.
 */
@Service
public class GoogleCalendarService implements CalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCalendarService.class);

    //<service account email address>@developer.gserviceaccount.com
    private String serviceAccountId;

    //<private key for service account in P12 format>-privatekey.p12
    private String keyFilePath;

    //"<domain user whose data you need>@yourdomain.com"
    private String serviceAccountUser;

    private String calendarId;

    private Calendar calendar = null;

    @PostConstruct
    public void setup() {
        GoogleCredential credentials;
        HttpTransport httpTransport;
        File keyFile;
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            keyFile = new File(keyFilePath);
            credentials = new GoogleCredential.Builder().setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(this.serviceAccountId)
                    .setServiceAccountScopes(Arrays.asList(CalendarScopes.CALENDAR))
                    .setServiceAccountPrivateKeyFromP12File(keyFile)
                    .setServiceAccountUser(serviceAccountUser)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            LOGGER.error("Couldn't create Google Calendar credentials: ", e);
            return;
        }
        calendar = new Calendar.Builder(httpTransport, jsonFactory, credentials).build();
    }

    public void createEvent() {
        try {
            Event event = new Event();
            calendar.events().insert(calendarId, event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
