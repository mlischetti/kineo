package com.kineo.repository.impl;

import com.kineo.model.MedicalInsuranceCompany;
import com.kineo.repository.MedicalInsuranceCompanyRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mlischetti on 12/8/15
 */
@Repository
public class MedicalInsuranceCompanyRepositoryImpl extends GenericRepository<MedicalInsuranceCompany> implements MedicalInsuranceCompanyRepository {

    @Autowired
    public MedicalInsuranceCompanyRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<MedicalInsuranceCompany> find(int firstResult, int maxResults) {
        Criteria criteria = getCriteria();
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
        return criteria.list();
    }
}
