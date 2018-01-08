package com.jbridgiee.data.access.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jbridgiee.data.model.Film;

/**
 *
 * @author josh.bridge
 */
@Repository
public class FilmDatastoreDAO {

    public FilmDatastoreDAO() {
        ObjectifyService.register(Film.class);
    }

    public void create(Film item) {
        getConnection().save().entities(item).now();
    }

    public void delete(Film item) {
        getConnection().delete().entities(item).now();
    }

    public Stream<Film> getAll() {
        return getConnection().load().type(Film.class).order("title").list().stream();
    }

    public Optional<Film> getById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(getConnection().load().key(Key.create(Film.class, id)).now());
    }

    public Stream<Film> searchItems(String searchTerm) {
        final List<Film> films = getConnection().load().type(Film.class).list();

        return films.stream()
                .filter(film -> film.getTitle().toLowerCase().contains(searchTerm.toLowerCase()));
    }

    private Objectify getConnection() {
        return ObjectifyService.ofy();
    }

}
