package com.kinesio.repository.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by mlischetti on 11/27/15.
 */
@SuppressWarnings("unchecked")
public abstract class Query<T, HQ extends org.hibernate.Query, Q extends Query<T, HQ, Q>> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Query.class);

    protected final HQ hQuery;


    public Query(HQ hQuery) {
        this.hQuery = hQuery;
    }

    public List<T> list() {
        return this.hQuery.list();
    }

    public T uniqueResult() {
        return (T) this.hQuery.uniqueResult();
    }

    public int executeUpdate() {
        return this.hQuery.executeUpdate();
    }

    public Q setParameter(int position, Object value) {
        if (value instanceof Collection<?>) {
            throw new IllegalArgumentException("Can not set collection param with position");
        } else {
            this.hQuery.setParameter(position, value);
        }
        return (Q) this;
    }

    public Q setParameters(Object... values) {
        int position = 0;
        for (Object value : values) {
            this.setParameter(position, value);
            position++;
        }
        return (Q) this;
    }

    public Q setParameter(String name, Object val) {
        if (val instanceof Collection<?>) {
            LOGGER.warn("Setting collection param {} as parameter, should be used parameter list", name);
            this.hQuery.setParameterList(name, (Collection<?>) val);
        } else {
            this.hQuery.setParameter(name, val);
        }
        return (Q) this;
    }

    public Q setParameterList(String name, Collection<?> values) {
        this.hQuery.setParameterList(name, values);
        return (Q) this;
    }

    public Q setParameterList(String name, Object[] values) {
        this.hQuery.setParameterList(name, values);
        return (Q) this;
    }

    public Q setParameterMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> param : map.entrySet()) {
            String paramName = param.getKey();
            Object paramValue = param.getValue();
            if (paramValue instanceof Collection<?>) {
                this.setParameterList(paramName, (Collection<?>) paramValue);
            } else {
                this.setParameter(paramName, paramValue);
            }
        }

        return (Q) this;
    }

    public Q setMaxResults(int maxResults) {
        this.hQuery.setMaxResults(maxResults);
        return (Q) this;
    }
}
