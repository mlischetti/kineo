package com.kineo.persistence.configuration;

import com.kineo.persistence.exception.DuplicateEntryException;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Created by mlischetti on 11/29/15.
 */
public class MySqlDuplicateEntriesAwareLocalSessionFactoryBean extends LocalSessionFactoryBean {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    @Override
    protected DataAccessException convertHibernateAccessException(HibernateException ex) {
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException jdbcEx = (ConstraintViolationException) ex;
            if (jdbcEx.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                return new DuplicateEntryException(
                        ex.getMessage() + "; SQL [" + jdbcEx.getSQL() + "]; constraint [" + jdbcEx.getConstraintName() + "]",
                        ex);
            }
        }
        return super.convertHibernateAccessException(ex);
    }
}
