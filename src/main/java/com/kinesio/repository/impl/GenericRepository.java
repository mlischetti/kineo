package com.kinesio.repository.impl;

import com.kinesio.repository.BaseRepository;
import com.kinesio.repository.query.SqlQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mlischetti on 11/29/15.
 */
public abstract class GenericRepository<T> implements BaseRepository<T> {

    private SessionFactory sessionFactory;

    @Autowired
    public GenericRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public <U> SqlQuery<U> sqlQuery(String query) {
        return new SqlQuery<U>(this.getCurrentSession().createSQLQuery(query));
    }

    public void save(T entity) {
        Session session = this.getCurrentSession();
        // If managed entity => within the session
        if (session.contains(entity)) {
            // Not allowed, let the ORM handle updates later
            throw new IllegalStateException(
                    "Cannot save entity '" + entity.getClass().getSimpleName() + "' because it is already managed by the ORM");
        }
        // Save or update the entity and attach it to the session
        session.saveOrUpdate(entity);
    }

    public void remove(T entity) {
        this.getCurrentSession().delete(entity);
    }
}
