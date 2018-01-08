package com.jbridgiee.data.access;

import com.jbridgiee.data.access.dao.FilmDatastoreDAO;
import com.jbridgiee.data.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author josh.bridge
 */
@Service
public class FilmService implements FilmInfo {

    private static final Logger LOGGER = Logger.getLogger(FilmService.class.getName());

    private final FilmDatastoreDAO filmDAO;

    @Autowired
    public FilmService(FilmDatastoreDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    public void addFilm(Film film) {
        try {
            film.setId(null);
            filmDAO.create(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError();
        }
    }

    @Override
    public List<Film> listFilm() {
        try {
            return filmDAO.getAll().collect(Collectors.toList());
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError();
        }
    }

    @Override
    public List<Film> searchFilms(String searchTerm) {
        try {
            return filmDAO.searchItems(searchTerm).collect(Collectors.toList());
        } catch (final Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public Optional<Film> getById(Long id) {
        try {
            return filmDAO.getById(id);
        } catch (final Exception e) {
            LOGGER.log(Level.INFO, e, e::getMessage);
            throw new InternalError();
        }
    }

    @Override
    public void updateFilm(Film film) {
        try {
            filmDAO.create(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError();
        }
    }

    @Override
    public void deleteFilm(Film film) {
        try {
            filmDAO.delete(film);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e, e::getMessage);
            throw new InternalError();
        }
    }

}
