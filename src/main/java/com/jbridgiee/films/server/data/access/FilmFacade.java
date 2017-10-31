package com.jbridgiee.films.server.data.access;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.jbridgiee.films.server.aop.LogResponseTime;
import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.dao.FilmDAO;
import com.jbridgiee.films.server.data.result.Result;
import com.jbridgiee.films.server.data.search.FilmSearchFactory;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmFacade implements FilmInfo {

    private final FilmDAO filmDAO;

    @Autowired
    public FilmFacade(FilmDAO filmDAO) {
        this.filmDAO = filmDAO;
    }

    @Override
    @LogResponseTime
    public void addFilm(Film film) {

    }

    @Override
    @LogResponseTime
    public Result<List<Film>> listFilm() {
        return Result.fromList(filmDAO.getAll());
    }

    @Override
    @LogResponseTime
    public Result<List<Film>> searchFilm(String searchTerm) {
        final List<Search<?>> searches = FilmSearchFactory.searchAll(searchTerm);

        final List<Film> results = Lists.newArrayList();
        for (final Search<?> search : searches) {
            results.addAll(filmDAO.searchItems(search));
        }

        return Result.fromList(removeDuplicates(results));
    }

    @Override
    @LogResponseTime
    public Result<Film> getById(int id) {
        return filmDAO.getById(id).map(Result::from).orElseGet(Result::emptyResult);
    }

    private List<Film> removeDuplicates(List<Film> films) {
        return films.stream().collect(collectingAndThen(toCollection(this::getUniqueFilms), Lists::newArrayList));
    }

    private TreeSet<Film> getUniqueFilms() {
        return new TreeSet<>(comparingInt(Film::getId));
    }

}
