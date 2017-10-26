package server;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

/**
 *
 * @author josh.bridge
 */
@RestController
public class WebController {

    private final FilmInfo filmInfo;

    private final GsonBuilder jsonBuilder;

    private final ResultFactory resultFactory;

    @Autowired
    public WebController(FilmInfo filmInfo, ResultFactory resultFactory) {
        this.filmInfo = filmInfo;
        this.jsonBuilder = new GsonBuilder();
        this.resultFactory = resultFactory;
    }

    @RequestMapping("/details/{idParam}")
    public String details(@PathVariable String idParam) {
        final int id = Integer.parseInt(idParam);

        if (id >= 0) {
            final Optional<Film> film = filmInfo.getById(id);

            if (film.isPresent()) {
                return jsonBuilder.create().toJson(resultFactory.create(film.get()));
            }
        }

        return "Invalid id";
    }

    @RequestMapping("/all")
    public String all() {
        return jsonBuilder.create().toJson(filmInfo.listFilm());
    }

}
