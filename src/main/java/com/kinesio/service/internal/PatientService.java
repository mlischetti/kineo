package com.kinesio.service.internal;

import com.kinesio.model.Patient;

import java.util.List;

/**
 * Created by mlischetti on 12/7/15.
 */
public interface PatientService {

    Patient findById(Long id);

    void save(Patient patient);

    List<Patient> find(int firstResult, int maxResults);

    Long count();
}
