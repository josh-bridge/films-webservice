package com.jbridgiee.data.access.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jbridgiee.data.model.Film;
import com.jbridgiee.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Repository
public class FilmObjectifyDAO {

    public FilmObjectifyDAO() {
        ObjectifyService.register(Film.class);
    }

    public void create(Film item) throws Exception {
        getConnection().save().entities(item).now();
    }

    public void update(Film item) throws Exception {
        create(item);
    }

    public void delete(Film item) throws Exception {
        getConnection().delete().entities(item).now();
    }

    public Optional<Film> getById(Long id) throws Exception {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(getConnection().load().key(Key.create(Film.class, id)).now());
    }

    public Stream<Film> getAll() throws Exception {
        return getConnection().load().type(Film.class).list().stream();
    }

    public Stream<Film> searchItems(Search search) throws Exception {
        return getConnection().load().type(Film.class).filter("title =", search.getFields().get(0)).list().stream();
    }

    private Objectify getConnection() {
        return ObjectifyService.ofy();
    }

}
