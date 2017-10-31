package com.jbridgiee.films.server.controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.data.access.FilmInfo;
import com.jbridgiee.films.server.data.result.Result;

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

        return createResponse(filmProvider.getById(id));
    }

    @RequestMapping("/all")
    public String all(HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(filmProvider.listFilm());
    }

    @RequestMapping("/search/{term}")
    public String search(@PathVariable String term, HttpServletResponse httpResponse) {
        setHeader(httpResponse);

        return createResponse(filmProvider.searchFilm(sanitise(term)));
    }

    abstract <R extends Result> String createResponse(R result);

    private void setHeader(HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, contentType.toString());
    }

    private String sanitise(String searchTerm) {
        try {
            return URLDecoder.decode(searchTerm, UTF_8);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode search term", e);
        }
    }

}
