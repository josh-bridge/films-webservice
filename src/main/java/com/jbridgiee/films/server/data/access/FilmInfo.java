package com.jbridgiee.films.server.data.access;

import java.util.List;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.result.Result;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    void addFilm(Film film);

    Result<List<Film>> listFilm();

    Result<List<Film>> searchFilm(String searchTerm);

    Result<Film> getById(int id);

}
