package server.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import server.Film;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmDAO {

    private static final RowMapper<Film> FILM_ROW_MAPPER = (row, rowNum) -> new Film(
            row.getInt("id"),
            WordUtils.capitalizeFully(row.getString("title")),
            row.getInt("year"),
            WordUtils.capitalizeFully(row.getString("director")),
            getNames(row.getString("stars")),
            row.getString("review"));

    private static List<String> getNames(String allNames) {
        return Arrays.stream(allNames.split(", "))
                .map(WordUtils::capitalizeFully)
                .collect(Collectors.toList());
    }

    private final BasicDataSource connectionPool;

    @Autowired
    public FilmDAO(BasicDataSource connectionPool) throws IOException {
        this.connectionPool = connectionPool;
    }

    public List<Film> getAllFilms() throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionPool);

        return jdbcTemplate.query("select * from films", FILM_ROW_MAPPER);
    }

    public Film getSingleFilm(int id) throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionPool);

        return jdbcTemplate.queryForObject("select * from films where id = ?", new Object[] { id }, FILM_ROW_MAPPER);
    }

}
