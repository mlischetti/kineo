package com.kinesio.repository;

/**
 * Created by mlischetti on 11/29/15.
 */
public interface BaseRepository<T> {

    T findById(Long id);

    T save(T entity);

    void remove(T entity);
}
