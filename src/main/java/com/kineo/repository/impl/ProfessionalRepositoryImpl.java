package com.kineo.repository.impl;

import com.kineo.model.Professional;
import com.kineo.repository.ProfessionalRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mlischetti on 11/29/15.
 */
@Repository
public class ProfessionalRepositoryImpl extends GenericRepository<Professional> implements ProfessionalRepository {

    @Autowired
    public ProfessionalRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Professional> find(int firstResult, int maxResults) {
        Criteria criteria = getCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }
}
