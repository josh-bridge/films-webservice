package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author josh.bridge
 */
@RestController
public class WebController {

    private final FilmInfo filmInfo;

    @Autowired
    public WebController(FilmInfo filmInfo) {
        this.filmInfo = filmInfo;
    }

    @RequestMapping("/test")
    public String home(@RequestParam(name = "test", defaultValue = "rolf") String name) {
        return "This is a test; name = " + name + "; FilmInfo: " + filmInfo.listFilm().iterator().next();
    }

}
