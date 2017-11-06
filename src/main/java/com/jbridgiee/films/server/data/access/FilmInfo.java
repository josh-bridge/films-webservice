package com.jbridgiee.films.server.data.access;

import java.util.List;
import java.util.Optional;

import com.jbridgiee.films.server.data.Film;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Optional<Film> getById(int id);

    List<Film> listFilm();

    List<Film> searchFilm(String searchTerm);

    List<Film> searchFilms(String field, String term);

}
