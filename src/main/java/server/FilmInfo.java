package server;

import java.util.Collection;

/**
 *
 * @author josh.bridge
 */
public interface FilmInfo {

    void addFilm(Film film);

    Collection<Film> listFilm();

    Collection<Film> searchFilm(String search);

}
