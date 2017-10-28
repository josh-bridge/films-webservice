package server.data;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import server.Film;

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
        try {
            return filmDAO.getAllFilms();
        } catch (final SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Film> searchFilm(String search) {
        return null;
    }

    @Override
    public Optional<Film> getById(int id) {
        try {
            return Optional.ofNullable(filmDAO.getSingleFilm(id));
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }
}
