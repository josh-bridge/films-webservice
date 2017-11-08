package com.jbridgiee.films.server.data.access.dao.sql;

import static com.jbridgiee.films.server.data.access.dao.sql.SqlStatement.sql;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Repository
public class FilmSqlDAO extends SqlDAO<Film> {

    private static final int LOWEST_FILM_ID = 10000;

    private static final String ID = "id";

    private static final String FILMS = "films";

    public static final String TITLE = "title";

    public static final String YEAR = "year";

    public static final String DIRECTOR = "director";

    public static final String STARS = "stars";

    private static final String REVIEW = "review";

    private static final String VALUE = "?";

    private static final SqlStatement GET_FILM_BY_ID = sql()
            .selectAll()
            .from(FILMS)
            .where(ID)
            .eq(VALUE);

    private static final SqlStatement GET_ALL_FILMS = sql()
            .selectAll()
            .from(FILMS);

    @Autowired
    public FilmSqlDAO(BasicDataSource connectionPool) throws IOException {
        super(connectionPool);
    }

    @Override
    public Film create(Film item) {
        final int id = create(getInsertFilm(), getParams(item));

        return createNewFilm(item, id);
    }

    @Override
    public Film update(Film item) {
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
        throw new NotImplementedException("delete(film) has not been implemented");
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

    private String getSearchTerm(String search) {
        return "%" + search.trim() + "%";
    }

    private Object[] getSearchTerms(Search search) {
        final List<String> terms = Lists.newArrayList();

        for (int i = 0; i < search.getFields().size(); i++) {
            terms.add(getSearchTerm(search.getTerm()));
        }

        return terms.toArray(new Object[terms.size()]);
    }

    private Map<String, String> getParams(Film item) {
        final HashMap<String, String> params = Maps.newHashMap();

        params.put(TITLE, item.getTitle());
        params.put(YEAR, Integer.toString(item.getYear()));
        params.put(DIRECTOR, item.getDirector());
        params.put(STARS, String.join(", ", item.getStars()));
        params.put(REVIEW, item.getDescription());

        return params;
    }

    private SimpleJdbcInsert getInsertFilm() {
        return new SimpleJdbcInsert(getTemplate())
                .withTableName(FILMS)
                .usingColumns(TITLE, YEAR, DIRECTOR, STARS, REVIEW)
                .usingGeneratedKeyColumns(ID);
    }

    private Film createNewFilm(Film item, int id) {
        return new Film(
                id,
                item.getTitle(),
                item.getYear(),
                item.getDirector(),
                item.getStars(),
                item.getDescription());
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
