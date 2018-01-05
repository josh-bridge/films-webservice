package com.jbridgiee.data.access;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbridgiee.data.access.dao.FilmObjectifyDAO;
import com.jbridgiee.data.model.Film;
import com.jbridgiee.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Service
public class FilmService implements FilmInfo {

    private static final Logger LOGGER = Logger.getLogger(FilmService.class.getName());

    private final FilmObjectifyDAO filmDAO;

    @Autowired
    public FilmService(FilmObjectifyDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    public void addFilm(Film film) {
        try {
            film.setId(null);
            filmDAO.create(film);
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
            throw new InternalError(e);
        }
    }

    @Override
    public List<Film> searchFilm(String searchTerm) {
        final Search search = Search.create(searchTerm, "");

        try {
            return filmDAO.searchItems(search).collect(Collectors.toList());
        } catch (final Exception e) {
            throw new InternalError(e);
        }
    }

    @Override
    public Optional<Film> getById(Long id) {
        try {
            return filmDAO.getById(id);
        } catch (final Exception e) {
            LOGGER.log(Level.INFO, e, e::getMessage);
            throw new InternalError(e);
        }
    }

    @Override
    public List<Film> searchFilms(String field, String term) {
        if (isColumn(field)) {
            final Search search = Search.create(term, field.toLowerCase());

            try {
                return filmDAO.searchItems(search).collect(Collectors.toList());
            } catch (final Exception e) {
                LOGGER.log(Level.SEVERE, e, e::getMessage);
                throw new InternalError(e);
            }
        }

        return null;
    }

    @Override
    public void updateFilm(Film film) {
        try {
            filmDAO.create(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError(e);
        }
    }

    @Override
    public void deleteFilm(Film film) {
        try {
            filmDAO.delete(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError(e);
        }
    }

    private boolean isColumn(String field) {
        return false;
    }

}
