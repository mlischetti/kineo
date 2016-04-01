package com.kineo.service.internal;

import com.kineo.model.Doctor;

import java.util.List;

/**
 * Created by mlischetti on 12/6/15.
 */
public interface DoctorService {

    Doctor findById(Long id);

    void save(Doctor doctor);

    List<Doctor> find(int firstResult, int maxResults);

    Long count();

    void delete(Doctor doctor);
}
