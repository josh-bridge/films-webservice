package com.jbridgiee.films.server.data;

import java.util.List;
import java.util.Optional;

import com.jbridgiee.films.server.Film;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    void addFilm(Film film);

    List<Film> listFilm();

    List<Film> searchFilm(String searchTerm);

    Optional<Film> getById(int id);

}
