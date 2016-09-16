package com.kineo.web.response.appointment;

import com.kineo.web.dto.AppointmentDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlischetti on 16/09/16.
 */
public class Appointments {
    private Criteria criteria;
    private List<AppointmentDto> items = new ArrayList<>();

    public Appointments() {

    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<AppointmentDto> getItems() {
        return items;
    }

    public void setItems(List<AppointmentDto> items) {
        this.items = items;
    }
}
