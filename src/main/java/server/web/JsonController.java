package server.web;

import static com.google.common.net.MediaType.JSON_UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

import server.data.FilmInfo;
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
    public JsonController(FilmInfo filmProvider) {
        super(filmProvider, JSON_UTF_8);
        this.jsonBuilder = new GsonBuilder();
    }

    @Override
    <R extends Result> String createResponse(R result) {
        return jsonBuilder.create().toJson(result);
    }

}
