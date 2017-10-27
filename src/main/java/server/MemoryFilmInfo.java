package server;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 *
 * @author josh.bridge
 */
@Component
public class MemoryFilmInfo implements FilmInfo {

    private final List<Film> films = Lists.newArrayList(
            new Film(43, "Pulp Fiction", 1994, 154),
            new Film(44, "Inception", 2010, 148));

    public void addFilm(Film film) {
        films.add(film);
    }

    @Nullable
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
