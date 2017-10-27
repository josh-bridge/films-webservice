package server;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.APPLICATION_XML_UTF_8;
import static com.google.common.net.MediaType.JSON_UTF_8;
import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.net.MediaType;
import com.google.gson.GsonBuilder;

import server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
public class WebController {

    private static final String TEXT = "text";

    private final FilmInfo filmInfo;

    private final GsonBuilder jsonBuilder;

    private final XmlMapper xmlMapper;

    @Autowired
    public WebController(FilmInfo filmInfo) {
        this.filmInfo = filmInfo;
        this.jsonBuilder = new GsonBuilder();
        this.xmlMapper = new XmlMapper();
    }

    @RequestMapping("/details/{id}")
    public String details(@PathVariable int id, @RequestParam(name = "type", defaultValue = TEXT) String type, HttpServletResponse httpResponse) {
        final Result<Film> result = getResult(id);

        return createResponse(result, type, httpResponse);
    }

    @RequestMapping("/all")
    public String all(@RequestParam(name = "type", defaultValue = TEXT) String type, HttpServletResponse httpResponse) {
        final Result<List<Film>> result = Result.from(filmInfo.listFilm());

        return createResponse(result, type, httpResponse);
    }

    private <T extends Result> String createResponse(T result, String type, HttpServletResponse response) {
        final MediaType contentType = getContentType(type);

        response.setHeader(CONTENT_TYPE, contentType.toString());

        String textresponse = "";
        try {
            textresponse = buildResponse(result, contentType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return textresponse;
    }

    private MediaType getContentType(String type) {
        if (JSON_UTF_8.subtype().equalsIgnoreCase(type)) {
            return JSON_UTF_8;
        }

        if (APPLICATION_XML_UTF_8.subtype().equalsIgnoreCase(type)) {
            return APPLICATION_XML_UTF_8;
        }

        return PLAIN_TEXT_UTF_8;
    }

    private <T> String buildResponse(T result, MediaType contentType) throws JsonProcessingException {
        if (contentType.is(JSON_UTF_8)) {
            return jsonBuilder.create().toJson(result);
        }

        if (contentType.is(APPLICATION_XML_UTF_8)) {
            // final ObjectWriter writer = xmlMapper.writer();
            // final SerializationConfig config = writer.getConfig();

            return xmlMapper.writer().withRootName("result").writeValueAsString(result);
        }
        return result.toString();
    }

    private Result<Film> getResult(int id) {
        if (id < 0) {
            return Result.emptyResult();
        }

        return filmInfo.getById(id)
                .map(Result::from)
                .orElseGet(Result::emptyResult);
    }

}
