package com.kineo.web.dto;

import com.kineo.model.CalendarEvent;
import com.kineo.model.CalendarEventStatus;

/**
 * Created by Mariano on 1/31/2016.
 */
public class CalendarEventDto {
    private Long id;
    private String eventId;
    private CalendarEventStatus status;

    public CalendarEventDto(CalendarEvent event) {
        this.id = event.getId();
        this.eventId = event.getEventId();
        this.status = event.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public CalendarEventStatus getStatus() {
        return status;
    }

    public void setStatus(CalendarEventStatus status) {
        this.status = status;
    }
}
