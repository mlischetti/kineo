package com.kineo.service.internal.impl;

import com.kineo.model.Patient;
import com.kineo.repository.PatientRepository;
import com.kineo.service.internal.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    @Override
    public List<Patient> find(int firstResult, int maxResults) {
        return patientRepository.find(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Long count() {
        return patientRepository.count();
    }

    @Override
    public void delete(Patient patient) {
        patient.setDeleted(true);
        patientRepository.save(patient);
    }
}
