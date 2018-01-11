package com.jbridgiee.model.access;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.jbridgiee.model.Film;

/**
 * A persistent storage accessor for Google Datastore
 *
 * @author josh.bridge
 */
@Repository
public class FilmDatastoreDAO implements FilmDAO {

    public FilmDatastoreDAO() {
        ObjectifyService.register(Film.class);
    }

    /**
     * Create a new film entity in the datastore
     *
     * <p>
     *     Datastore requires the ID to be null for it to auto-generate one
     * </p>
     *
     * @param item the film to add
     */
    @Override
    public void create(Film item) {
        item.setId(null);
        getConnection().save().entities(item).now();
    }

    /**
     * Update a film entity in Google Datastore
     *
     * <p>
     *     Requires that the ID in the object is a valid resource
     * </p>
     *
     * @param item the film to update
     */
    @Override
    public void update(Film item) {
        getConnection().save().entities(item).now();
    }

    /**
     * Delete a film entity in Google Datastore
     *
     * <p>
     *     Requires that the ID in the object is a valid resource
     * </p>
     *
     * @param item the film to update
     */
    @Override
    public void delete(Film item) {
        getConnection().delete().entities(item).now();
    }

    /**
     * Retrieve all film entities from Google Datastore
     *
     * <p>
     *     Ordered by title: 0-9 -> A-Z -> a-z
     * </p>
     *
     * @return A stream with zero to many film objects
     */
    @Override
    public Stream<Film> getAll() {
        return getConnection().load().type(Film.class).order("title").list().stream();
    }

    /**
     * Retrieve a film from Google Datastore by its ID
     *
     * @param id the ID of the film to retrieve
     * @return An optional with the film, or an empty optional if no film found
     */
    @Override
    public Optional<Film> getById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(getConnection().load().key(Key.create(Film.class, id)).now());
    }

    /**
     * Retrieve all films and filter by a search term
     *
     * <p>
     *     Google Datastore doesn't allow text search by default, so this simply filters them using java
     * </p>
     *
     * @param searchTerm the value to search for
     * @return all matching films
     */
    @Override
    public Stream<Film> search(String searchTerm) {
        final List<Film> films = getConnection().load().type(Film.class).list();

        return films.stream()
                .filter(film -> film.getTitle().toLowerCase().contains(searchTerm.toLowerCase()));
    }

    /**
     * Get the connection to the datastore
     *
     * @return the connection
     */
    private Objectify getConnection() {
        return ObjectifyService.ofy();
    }

}
