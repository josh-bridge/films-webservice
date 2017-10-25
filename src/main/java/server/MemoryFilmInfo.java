package server;

import java.time.Year;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

/**
 *
 * @author josh.bridge
 */
@Component
public class MemoryFilmInfo implements FilmInfo {

    private final Map<String, Film> films = Maps.newHashMap();

    public void addFilm(Film film) {
        films.put(film.getTitle(), film);
    }

    public Collection<Film> listFilm() {
        return Collections.singletonList(
                new Film(1,
                        "Pulp Fiction",
                         Year.of(1994),
                         154,
                         "fakeUrl",
                         new Genres(Genre.ACTION)));
    }

    public Collection<Film> searchFilm(String search) {
        return Collections.singletonList(films.get(search));
    }

}
