package com.jbridgiee.films.server.data.access;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.result.Result;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    Result addFilm(Film film);

    Result listFilm();

    Result searchFilm(String searchTerm);

    Result getById(int id);

    Result searchFilms(String field, String term);

}
