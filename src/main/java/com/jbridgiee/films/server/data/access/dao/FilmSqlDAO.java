package com.jbridgiee.films.server.data.access.dao;

import static com.jbridgiee.films.server.data.access.dao.sql.SqlStatement.sql;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.dao.sql.SqlStatement;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmSqlDAO extends SqlDAO<Film> {

    private static final int LOWEST_FILM_ID = 10000;

    private static final String FILMS = "films";

    private static final String ID = "id";

    private static final String TITLE = "title";

    private static final String YEAR = "year";

    private static final String DIRECTOR = "director";

    private static final String STARS = "stars";

    private static final String REVIEW = "review";

    private static final String VALUE = "?";

    private static final SqlStatement GET_FILM_BY_ID = sql().selectAll().from(FILMS).where(ID).eq(VALUE);

    private static final SqlStatement GET_ALL_FILMS = sql().selectAll().from(FILMS);

    @Autowired
    public FilmSqlDAO(BasicDataSource connectionPool) throws IOException {
        super(connectionPool);
    }

    @Override
    public boolean create(Film item) {
        throw new NotImplementedException(this.getClass().getName() + ".create(film) has not been implemented");
    }

    @Override
    public boolean update(Film item) {
        throw new NotImplementedException(this.getClass().getName() + ".update(film) has not been implemented");
    }

    @Override
    public Optional<Film> getById(int id) {
        return (id < LOWEST_FILM_ID) ? Optional.empty() : Optional.ofNullable(getItem(GET_FILM_BY_ID, getFilmRowMapper(), id));
    }

    @Override
    public List<Film> getAll() {
        final List<Film> films = getItems(GET_ALL_FILMS, getFilmRowMapper());

        return films == null ? Collections.emptyList() : films;
    }

    @Override
    public List<Film> searchItems(Search<?> search) {
        return getItems(searchByField(search.getField()), getFilmRowMapper(), getSearchTerm(search));
    }

    @Override
    public boolean delete(Film item) {
        throw new NotImplementedException(this.getClass().getName() + ".delete(film) has not been implemented");
    }

    private SqlStatement searchByField(String field) {
        return sql().selectAll().from(FILMS).where(field).like(VALUE);
    }

    private String getSearchTerm(Search<?> search) {
        return "%" + search.getTerm().toString().trim() + "%";
    }

    private RowMapper<Film> getFilmRowMapper() {
        return (row, rowNum) -> new Film(
                row.getInt(ID),
                formatName(row.getString(TITLE)),
                row.getInt(YEAR),
                formatName(row.getString(DIRECTOR)),
                getNames(row.getString(STARS)),
                row.getString(REVIEW));
    }

    private List<String> getNames(String allNames) {
        return Arrays.stream(allNames.split(", "))
                .map(this::formatName)
                .collect(Collectors.toList());
    }

    private String formatName(String value) {
        return WordUtils.capitalizeFully(value, ' ', '-', '\'', '.');
    }

}
