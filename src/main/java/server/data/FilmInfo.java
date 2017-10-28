package server.data;

import java.util.List;
import java.util.Optional;

import server.Film;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    void addFilm(Film film);

    List<Film> listFilm();

    List<Film> searchFilm(String search);

    Optional<Film> getById(int id);

}
