package com.kinesio.repository.impl;

import com.kinesio.repository.BaseRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by mlischetti on 11/29/15.
 */
public abstract class GenericRepository<T> implements BaseRepository<T> {

    private static final String ID_FIELD = "id";

    private SessionFactory sessionFactory;

    private final Class<T> entityType;

    @Autowired
    public GenericRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Type t = this.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityType = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public T findById(Long id) {
        Criteria criteria = this.getCurrentSession().createCriteria(entityType);
        criteria.add(Restrictions.eq(ID_FIELD, id));
        return (T) criteria.uniqueResult();
    }

    public List<T> find(int firstResult, int maxResults) {
        Criteria criteria = this.getCurrentSession().createCriteria(entityType);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        return criteria.list();
    }

    public T save(T entity) {
        Session session = this.getCurrentSession();
        // If managed entity => within the session
        if (session.contains(entity)) {
            // Not allowed, let the ORM handle updates later
            throw new IllegalStateException(
                    "Cannot save entity '" + entity.getClass().getSimpleName() + "' because it is already managed by the ORM");
        }
        // Save or update the entity and attach it to the session
        session.saveOrUpdate(entity);
        return entity;
    }

    public void remove(T entity) {
        this.getCurrentSession().delete(entity);
    }

    public Long count() {
        Criteria criteria = this.getCurrentSession().createCriteria(entityType);
        Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return count;
    }
}
