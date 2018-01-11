package com.jbridgiee.model.access;

import java.util.Optional;
import java.util.stream.Stream;

import com.jbridgiee.model.Film;

/**
 * @author josh.bridge
 */
public interface FilmDAO {

    void create(Film item);

    void update(Film item);

    void delete(Film item);

    Stream<Film> getAll();

    Optional<Film> getById(Long id);

    Stream<Film> search(String searchTerm);
}
