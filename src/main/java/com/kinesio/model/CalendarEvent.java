package com.kinesio.model;

import javax.persistence.*;

/**
 * Created by Mariano on 1/31/2016.
 */
@Entity
@Table(name = "Calendar_Event")
public class CalendarEvent extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CalendarEventStatus status = CalendarEventStatus.PENDING;

    @Column(name = "event_id")
    private String eventId;

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
