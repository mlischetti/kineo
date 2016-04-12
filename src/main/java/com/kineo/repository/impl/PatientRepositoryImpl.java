package com.kineo.repository.impl;

import com.kineo.model.Patient;
import com.kineo.repository.PatientRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mlischetti on 12/7/15.
 */
@Repository
public class PatientRepositoryImpl extends GenericRepository<Patient> implements PatientRepository {

    @Autowired
    public PatientRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Patient> find(int firstResult, int maxResults) {
        Criteria criteria = getCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }
}
