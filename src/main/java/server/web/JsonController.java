package server.web;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.JSON_UTF_8;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

import server.FilmInfo;
import server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/json")
public class JsonController extends FilmController {

    private final GsonBuilder jsonBuilder;

    @Autowired
    public JsonController(FilmInfo filmInfo) {
        super(filmInfo);
        this.jsonBuilder = new GsonBuilder();
    }

    @Override
    public String details(@PathVariable int id, HttpServletResponse httpResponse) {
        return createResponse(getResult(id), httpResponse);
    }

    @Override
    public String all(HttpServletResponse httpResponse) {
        return createResponse(Result.from(this.getFilmInfo().listFilm()), httpResponse);
    }

    private <T extends Result> String createResponse(T result, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, JSON_UTF_8.toString());

        return jsonBuilder.create().toJson(result);
    }

}
