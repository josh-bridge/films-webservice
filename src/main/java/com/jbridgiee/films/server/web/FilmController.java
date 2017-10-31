package com.jbridgiee.films.server.web;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.Film;
import com.jbridgiee.films.server.data.FilmInfo;
import com.jbridgiee.films.server.result.Result;

public abstract class FilmController {

    private final FilmInfo filmProvider;

    private final MediaType contentType;

    FilmController(FilmInfo filmProvider, MediaType contentType) {
        this.filmProvider = filmProvider;
        this.contentType = contentType;
    }

    @RequestMapping("/details/{id}")
    public String details(@PathVariable int id, HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(getResult(id));
    }

    @RequestMapping("/all")
    public String all(HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(getResult(filmProvider.listFilm()));
    }

    @RequestMapping("/search/{term}")
    public String search(@PathVariable String term, HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(getResult(searchFilms(term)));
    }

    abstract <R extends Result> String createResponse(R result);

    private void setHeader(HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, contentType.toString());
    }

    private List<Film> searchFilms(String term) {
        try {
            return filmProvider.searchFilm(URLDecoder.decode(term, UTF_8));
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private Result<?> getResult(int id) {
        return (id > 10000) ? filmProvider.getById(id).map(Result::from).orElseGet(Result::emptyResult)
                : Result.emptyResult();
    }

    private Result<?> getResult(List<Film> films) {
        return (!films.isEmpty()) ? Result.from(films) : Result.emptyResult();
    }

}
