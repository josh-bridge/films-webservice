package server.web;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.FilmInfo;
import server.data.ConnectionPooler;
import server.data.SqlFilmDAO;
import server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/text")
public class TextController extends FilmController {

    private final SqlFilmDAO filmDAO;

    private final ConnectionPooler connectionPooler;

    @Autowired
    public TextController(FilmInfo filmInfo, SqlFilmDAO filmDAO, ConnectionPooler connectionPooler) {
        super(filmInfo);
        this.filmDAO = filmDAO;
        this.connectionPooler = connectionPooler;
    }

    @Override
    public String details(@PathVariable int id, HttpServletResponse httpResponse) {
        return createResponse(getResult(id), httpResponse);
    }

    @Override
    public String all(HttpServletResponse httpResponse) {
        return createResponse(Result.from(this.getFilmInfo().listFilm()), httpResponse);
    }

    @RequestMapping("/test")
    public String test() {
        final String result = filmDAO.openConnection();
        filmDAO.closeConnection();

        return "" + result;
    }

    @RequestMapping("/test2")
    public String test2() {
        String result = "";
        try {
            result = connectionPooler.executeQuery("select * from `films`");
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private <T extends Result> String createResponse(T result, HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, PLAIN_TEXT_UTF_8.toString());

        return result.toString();
    }

}
