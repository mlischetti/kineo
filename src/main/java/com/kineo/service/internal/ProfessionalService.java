package com.kineo.service.internal;

import com.kineo.model.Professional;

import java.util.List;

/**
 * Created by mlischetti on 12/6/15.
 */
public interface ProfessionalService {

    Professional findById(Long id);

    void save(Professional professional);

    List<Professional> find(int firstResult, int maxResults);

    Long count();

    void delete(Professional professional);
}
