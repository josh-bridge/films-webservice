package com.jbridgiee.data.access;

import java.util.List;
import java.util.Optional;

import com.jbridgiee.data.model.Film;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    void addFilm(Film film);

    void updateFilm(Film film);

    void deleteFilm(Film film);

    Optional<Film> getById(Long id);

    List<Film> listFilm();

    List<Film> searchFilms(String searchTerm);

}
