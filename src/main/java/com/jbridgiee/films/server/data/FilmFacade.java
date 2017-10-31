package com.jbridgiee.films.server.data;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.jbridgiee.films.server.Film;
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
    public void addFilm(Film film) {

    }

    @Override
    public List<Film> listFilm() {
        return filmDAO.getAll();
    }

    @Override
    public List<Film> searchFilm(String searchTerm) {
        final List<Search<?>> searches = FilmSearchFactory.searchAll(searchTerm);

        final List<Film> results = Lists.newArrayList();
        for (final Search<?> search : searches) {
            results.addAll(filmDAO.searchItems(search));
        }

        return removeDuplicates(results);
    }

    private List<Film> removeDuplicates(List<Film> films) {
        return films.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Film::getId))), ArrayList::new));
    }

    @Override
    public Optional<Film> getById(int id) {
        return filmDAO.getById(id);
    }
}
