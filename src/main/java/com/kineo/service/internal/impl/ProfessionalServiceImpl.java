package com.kineo.service.internal.impl;

import com.kineo.model.Professional;
import com.kineo.repository.ProfessionalRepository;
import com.kineo.service.internal.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mlischetti on 12/6/15.
 */
@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    @Autowired
    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Transactional
    @Override
    public Professional findById(Long id) {
        return professionalRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Professional professional) {
        professionalRepository.save(professional);
    }

    @Transactional
    @Override
    public List<Professional> find(int firstResult, int maxResults) {
        return professionalRepository.find(firstResult, maxResults);
    }

    @Transactional
    @Override
    public Long count() {
        return professionalRepository.count();
    }

    @Transactional
    @Override
    public void delete(Professional professional) {
        professional.setDeleted(true);
        save(professional);
    }
}
