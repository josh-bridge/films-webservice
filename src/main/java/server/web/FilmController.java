package server.web;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.MediaType;

import server.Film;
import server.data.FilmInfo;
import server.result.Result;

public abstract class FilmController {

    private final FilmInfo filmProvider;

    private final MediaType contentType;

    FilmController(FilmInfo filmProvider, MediaType contentType) {
        this.filmProvider = filmProvider;
        this.contentType = contentType;
    }

    @RequestMapping("/details/{id}")
    public String details(int id, HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(getResult(id));
    }

    @RequestMapping("/all")
    public String all(HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(getAllResults());
    }

    abstract <R extends Result> String createResponse(R result);

    private void setHeader(HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, contentType.toString());
    }

    private Result<?> getResult(int id) {
        return (id > 10000) ? filmProvider.getById(id).map(Result::from).orElseGet(Result::emptyResult)
                 : Result.emptyResult();
    }

    private Result<?> getAllResults() {
        final List<Film> films = filmProvider.listFilm();

        return (!films.isEmpty()) ? Result.from(films) : Result.emptyResult();
    }

}
