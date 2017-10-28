package server;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 *
 * @author josh.bridge
 */
@Component
public class MemoryFilmInfo implements FilmInfo {

    private final List<Film> films = Lists.newArrayList(
            new Film(43, "Pulp Fiction", 1994, "Quentin Tarantino", Collections.emptyList(), "is gd"),
            new Film(44, "Inception", 2010, "Christopher Nolan", Collections.emptyList(), "is gd lol"));

    public void addFilm(Film film) {
        films.add(film);
    }

    public List<Film> listFilm() {
        return films;
    }

    public List<Film> searchFilm(String search) {

        return films.stream()
                .filter((film) -> film.getTitle().equals(search))
                .collect(Collectors.toList());
    }

    public Optional<Film> getById(int id) {
        return films.stream()
                .filter((film) -> film.getId() == id)
                .findFirst();
    }

}
