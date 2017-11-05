package com.jbridgiee.films.server.data.access;

import static com.jbridgiee.films.server.data.access.dao.FilmSqlDAO.DIRECTOR;
import static com.jbridgiee.films.server.data.access.dao.FilmSqlDAO.STARS;
import static com.jbridgiee.films.server.data.access.dao.FilmSqlDAO.TITLE;
import static com.jbridgiee.films.server.data.access.dao.FilmSqlDAO.YEAR;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.dao.DAO;
import com.jbridgiee.films.server.data.access.dao.FilmSqlDAO;
import com.jbridgiee.films.server.data.result.Result;
import com.jbridgiee.films.server.data.search.FilmSearchFactory;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmFacade<T extends DAO<Film>> implements FilmInfo {

    private final T filmDAO;

    @Autowired
    public FilmFacade(T filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    public Result addFilm(Film film) {
        try {
            filmDAO.create(film);
            return Result.fromUpdate(film);
        } catch (final Exception e) {
            return Result.fromError(e);
        }
    }

    @Override
    public Result listFilm() {
        try {
            return Result.fromDataList(filmDAO.getAll().collect(Collectors.toList()));
        } catch (final Exception e) {
            return Result.fromError(e);
        }
    }

    @Override
    public Result searchFilm(String searchTerm) {
        final Search search = FilmSearchFactory.searchAll(searchTerm, TITLE, DIRECTOR, STARS, YEAR);

        try {
            return Result.fromDataList(filmDAO.searchItems(search).collect(Collectors.toList()));
        } catch (final Exception e) {
            return Result.fromError(e);
        }
    }

    @Override
    public Result getById(int id) {
        try {
            return filmDAO.getById(id).map(Result::fromData).orElseGet(Result::emptyResult);
        } catch (final Exception e) {
            return Result.fromError(e);
        }
    }

    @Override
    public Result searchFilms(String field, String term) {
        if (isColumn(field)) {
            final Search search = FilmSearchFactory.searchField(field.toLowerCase(), term);

            try {
                return Result.fromDataList(filmDAO.searchItems(search).collect(Collectors.toList()));
            } catch (final Exception e) {
                return Result.fromError(e);
            }
        }

        return Result.fromError("Could not find column: " + field, new Exception());
    }

    private boolean isColumn(String field) {
        return Arrays.stream(FilmSqlDAO.COLUMNS).anyMatch((column) -> column.equalsIgnoreCase(field));
    }

}
