package com.jbridgiee.films.server.data.access.dao;

import static com.jbridgiee.films.server.data.access.dao.sql.SqlStatement.sql;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
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

    public static final String ID = "id";

    public static final String FILMS = "films";

    public static final String TITLE = "title";

    public static final String YEAR = "year";

    public static final String DIRECTOR = "director";

    public static final String STARS = "stars";

    public static final String REVIEW = "review";

    public static final String[] COLUMNS = new String[] { ID, TITLE, YEAR, DIRECTOR, STARS, REVIEW };

    private static final String VALUE = "?";

    private static final SqlStatement GET_FILM_BY_ID = sql().selectAll().from(FILMS).where(ID).eq(VALUE);

    private static final SqlStatement GET_ALL_FILMS = sql().selectAll().from(FILMS);

    @Autowired
    public FilmSqlDAO(BasicDataSource connectionPool) throws IOException {
        super(connectionPool);
    }

    @Override
    public boolean create(Film item) {
        throw new NotImplementedException("create(film) has not been implemented");
    }

    @Override
    public boolean update(Film item) {
        throw new NotImplementedException("update(film) has not been implemented");
    }

    @Override
    public Optional<Film> getById(int id) {
        return (id < LOWEST_FILM_ID) ? Optional.empty() : Optional.ofNullable(getItem(GET_FILM_BY_ID, getFilmRowMapper(), id));
    }

    @Override
    public Stream<Film> getAll() {
        return getItems(GET_ALL_FILMS, getFilmRowMapper());
    }

    @Override
    public Stream<Film> searchItems(Search search) {
        return getItems(searchByFields(search.getFields()), getFilmRowMapper(), getSearchTerms(search));
    }

    @Override
    public boolean delete(Film item) {
        throw new NotImplementedException(this.getClass().getName() + ".delete(film) has not been implemented");
    }

    private SqlStatement searchByField(String field) {
        return sql().selectAll().from(FILMS).where(field).like(VALUE);
    }

    private SqlStatement searchByFields(List<String> fields) {
        final SqlStatement statement = sql().selectAll().from(FILMS);

        for (int i = 0; i < fields.size(); i++) {
            final String field = fields.get(i);

            if (i == 0) {
                statement.where(field).like(VALUE);
            } else {
                statement.or(field).like(VALUE);
            }
        }

        return statement.groupBy(ID);
    }

    private String getSearchTerm(Search search) {
        return "%" + search.getTerm().trim() + "%";
    }

    private Object[] getSearchTerms(Search search) {
        final List<String> terms = Lists.newArrayList();

        for (int i = 0; i < search.getFields().size(); i++) {
            terms.add(getSearchTerm(search));
        }

        return terms.toArray(new Object[terms.size()]);
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
