package com.jbridgiee.films.server.data.access.dao;

import java.util.Optional;
import java.util.stream.Stream;

import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
public interface DAO<T> {

    Stream<T> getAll() throws Exception;

    Optional<T> getById(int id) throws Exception;

    boolean create(T item) throws Exception;

    boolean update(T item) throws Exception;

    boolean delete(T item) throws Exception;

    Stream<T> searchItems(Search search) throws Exception;

}
