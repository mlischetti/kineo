package com.kinesio.repository.query;

import org.hibernate.SQLQuery;

/**
 * Created by mlischetti on 11/27/15.
 */
public class SqlQuery<T> extends Query<T, SQLQuery, SqlQuery<T>> {

    public SqlQuery(SQLQuery hQuery) {
        super(hQuery);
    }

    public SqlQuery<T> addScalars(String... columnAliases) {
        for (String columnAlias : columnAliases) {
            this.addScalar(columnAlias);
        }
        return this;
    }

    private void addScalar(String columnAlias) {
        this.hQuery.addScalar(columnAlias);
    }
}
