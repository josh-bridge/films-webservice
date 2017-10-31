package com.jbridgiee.films.server.data;

import java.util.List;
import java.util.Optional;

import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
public interface DAO<T> {

    boolean create(T item);

    boolean update(T item);

    Optional<T> getById(int id);

    List<T> getAll();

    Optional<T> searchItem(Search<?> search);

    List<T> searchItems(Search<?> search);

    boolean delete(T item);

}
