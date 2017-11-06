package com.jbridgiee.films.server.data.access;

import static com.jbridgiee.films.server.data.access.dao.sql.FilmSqlDAO.DIRECTOR;
import static com.jbridgiee.films.server.data.access.dao.sql.FilmSqlDAO.STARS;
import static com.jbridgiee.films.server.data.access.dao.sql.FilmSqlDAO.TITLE;
import static com.jbridgiee.films.server.data.access.dao.sql.FilmSqlDAO.YEAR;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.dao.DAO;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmFacade<T extends DAO<Film>> implements FilmInfo {

    private static final Logger LOGGER = Logger.getLogger(FilmFacade.class.getName());

    private static final String[] COLUMNS = { TITLE, DIRECTOR, STARS, YEAR };

    private final T filmDAO;

    @Autowired
    public FilmFacade(T filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    public Film addFilm(Film film) {
        try {
            return filmDAO.create(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError(e);
        }
    }

    @Override
    public List<Film> listFilm() {
        try {
            return filmDAO.getAll().collect(Collectors.toList());
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            return null;
        }
    }

    @Override
    public List<Film> searchFilm(String searchTerm) {
        final Search search = Search.create(searchTerm, COLUMNS);

        try {
            return filmDAO.searchItems(search).collect(Collectors.toList());
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public Optional<Film> getById(int id) {
        try {
            return filmDAO.getById(id);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            return Optional.empty();
        }
    }

    @Override
    public List<Film> searchFilms(String field, String term) {
        if (isColumn(field)) {
            final Search search = Search.create(field.toLowerCase(), term);

            try {
                return filmDAO.searchItems(search).collect(Collectors.toList());
            } catch (final Exception e) {
                LOGGER.log(Level.SEVERE, e, e::getMessage);
                return null;
            }
        }

        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        try {
            return filmDAO.update(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError(e);
        }
    }

    private boolean isColumn(String field) {
        return Arrays.stream(COLUMNS).anyMatch((column) -> column.equalsIgnoreCase(field));
    }

}
