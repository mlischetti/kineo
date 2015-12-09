package com.kinesio.service.internal;

import com.kinesio.model.Doctor;

import java.util.List;

/**
 * Created by mlischetti on 12/6/15.
 */
public interface DoctorService {

    Doctor findById(Long id);

    void save(Doctor doctor);

    List<Doctor> find(int firstResult, int maxResults);

    Long count();
}
