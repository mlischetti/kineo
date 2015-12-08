package com.kinesio.service.internal.impl;

import com.kinesio.model.Patient;
import com.kinesio.repository.PatientRepository;
import com.kinesio.service.internal.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mlischetti on 12/7/15.
 */
@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Patient patient) {
        patientRepository.save(patient);
    }
}
