package server.web;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.APPLICATION_XML_UTF_8;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import server.Film;
import server.FilmInfo;
import server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/xml")
public class XmlController extends FilmController {

    private final XmlMapper xmlMapper;

    @Autowired
    public XmlController(FilmInfo filmInfo) {
        super(filmInfo);
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public String details(@PathVariable int id, HttpServletResponse httpResponse) {
        String response = "";
        try {
            response = createResponse(getResult(id), httpResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String all(HttpServletResponse httpResponse) {
        final Result<List<Film>> result = Result.from(this.getFilmInfo().listFilm());

        String response = "";
        try {
            response = createResponse(result, httpResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private <T extends Result> String createResponse(T result, HttpServletResponse response) throws JsonProcessingException {
        response.setHeader(CONTENT_TYPE, APPLICATION_XML_UTF_8.toString());

        return xmlMapper.writer().withRootName("result").writeValueAsString(result);
    }

}
