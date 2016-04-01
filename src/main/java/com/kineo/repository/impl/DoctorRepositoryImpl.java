package com.kineo.repository.impl;

import com.kineo.model.CalendarEventStatus;
import com.kineo.model.Doctor;
import com.kineo.repository.DoctorRepository;
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
public class DoctorRepositoryImpl extends GenericRepository<Doctor> implements DoctorRepository {

    @Autowired
    public DoctorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Doctor> find(int firstResult, int maxResults) {
        Criteria criteria = getCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }
}
