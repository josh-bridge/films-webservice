package server.web;

import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.data.FilmInfo;
import server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/text")
public class TextController extends FilmController {

    @Autowired
    public TextController(FilmInfo filmProvider) {
        super(filmProvider, PLAIN_TEXT_UTF_8);
    }

    @Override
    <R extends Result> String createResponse(R result) {
        return result.toString();
    }

}
