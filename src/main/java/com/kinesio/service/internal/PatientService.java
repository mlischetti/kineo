package com.kinesio.service.internal;

import com.kinesio.model.Patient;

/**
 * Created by mlischetti on 12/7/15.
 */
public interface PatientService {

    Patient findById(Long id);

    Patient save(Patient patient);
}
