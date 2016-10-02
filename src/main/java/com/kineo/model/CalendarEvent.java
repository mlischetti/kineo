package com.kineo.model;

import javax.persistence.*;

@Entity
@Table(name = "CALENDAR_EVENT")
public class CalendarEvent extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CalendarEventStatus status = CalendarEventStatus.PENDING;

    @Column(name = "EVENT_ID")
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
