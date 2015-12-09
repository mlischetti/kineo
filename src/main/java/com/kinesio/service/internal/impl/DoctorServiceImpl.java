package com.kinesio.service.internal.impl;

import com.kinesio.model.Doctor;
import com.kinesio.repository.DoctorRepository;
import com.kinesio.service.internal.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mlischetti on 12/6/15.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public List<Doctor> find(int firstResult, int maxResults) {
        return doctorRepository.find(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Long count() {
        return doctorRepository.count();
    }
}
