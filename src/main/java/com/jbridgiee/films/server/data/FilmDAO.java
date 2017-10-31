package com.jbridgiee.films.server.data;

import static com.jbridgiee.films.server.data.sql.SqlStatementBuilder.sqlStatement;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.Film;
import com.jbridgiee.films.server.data.search.Search;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmDAO implements DAO<Film> {

    private static final String GET_ALL_FILMS = "SELECT * FROM films";

    private static final String GET_FILM_BY_ID = "SELECT * FROM films WHERE ID = ?";

    private static final String SEARCH_FILMS = "SELECT * FROM films WHERE ? LIKE ?";

    private static final String SEARCH_FILM = "SELECT * FROM films WHERE ? LIKE ? LIMIT 1";

    private static final String FILMS = "films";

    private static final String ID = "id";

    private static final String TITLE = "title";

    private static final String YEAR = "year";

    private static final String DIRECTOR = "director";

    private static final String STARS = "stars";

    private static final String REVIEW = "review";

    private static final String VALUE = "?";

    private final BasicDataSource connectionPool;

    @Autowired
    public FilmDAO(BasicDataSource connectionPool) throws IOException {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean create(Film item) {
        return false;
    }

    @Override
    public boolean update(Film item) {
        return false;
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(getFilm(sqlStatement().selectAll().from(FILMS).where(ID).eq(VALUE).build(), id));
    }

    @Override
    public List<Film> getAll() {
        return getFilms(GET_ALL_FILMS);
    }

    @Override
    public Optional<Film> searchItem(Search<?> search) {
        return Optional.ofNullable(getFilm(SEARCH_FILM, search.getField(), getSearchTerm(search)));
    }

    @Override
    public List<Film> searchItems(Search<?> search) {
        return getFilms(getSql(search.getField()), getSearchTerm(search));
    }

    @Override
    public boolean delete(Film item) {
        return false;
    }

    @Nullable
    private Film getFilm(String sql, Object... args) {
        return getTemplate().queryForObject(sql, args, getFilmRowMapper());
    }

    private List<Film> getFilms(String sql, Object... args) {
        return getTemplate().query(sql, args, getFilmRowMapper());
    }

    private RowMapper<Film> getFilmRowMapper() {
        return (row, rowNum) -> new Film(
                    row.getInt(ID),
                    capitaliseName(row.getString(TITLE)),
                    row.getInt(YEAR),
                    capitaliseName(row.getString(DIRECTOR)),
                    getNames(row.getString(STARS)),
                    row.getString(REVIEW));
    }

    private List<String> getNames(String allNames) {
        return Arrays.stream(allNames.split(", "))
                .map(this::capitaliseName)
                .collect(Collectors.toList());
    }

    private String capitaliseName(String value) {
        return WordUtils.capitalizeFully(value, ' ', '-', '\'');
    }

    private Object getSearchTerm(Search<?> search) {
        return "%" + search.getTerm().toString().trim() + "%";
    }

    private JdbcTemplate getTemplate() {
        return new JdbcTemplate(connectionPool);
    }

    private String getSql(String field) {
        return sqlStatement().selectAll().from(FILMS).where(field).like(VALUE).build();
    }

}
