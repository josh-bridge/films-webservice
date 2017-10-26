package server;

import java.net.MalformedURLException;
import java.net.URL;
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
        try {
            return Collections.singletonList(
                    new Film(1,
                             "Pulp Fiction",
                             Year.of(1994),
                             154,
                             new URL("http://google.co.uk"),
                             new Genres(Genre.ACTION)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Collection<Film> searchFilm(String search) {
        return Collections.singletonList(films.get(search));
    }

}
