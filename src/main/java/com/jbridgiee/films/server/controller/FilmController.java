package com.jbridgiee.films.server.controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.aop.annotations.AutoContentType;
import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.FilmInfo;
import com.jbridgiee.films.server.data.result.Result;

public abstract class FilmController extends Controller {

    private final FilmInfo filmProvider;

    FilmController(MediaType contentType, FilmInfo filmProvider) {
        super(contentType);
        this.filmProvider = filmProvider;
    }

    @AutoContentType
    @RequestMapping("/details/{id}")
    public String details(HttpServletResponse httpResponse, @PathVariable int id) {
        return createResponse(httpResponse, filmProvider.getById(id));
    }

    @AutoContentType
    @RequestMapping("/all")
    public String all(HttpServletResponse httpResponse) {
        return createResponse(httpResponse, filmProvider.listFilm());
    }

    @AutoContentType
    @RequestMapping("/search/{term}")
    public String search(HttpServletResponse httpResponse, @PathVariable String term) {
        return createResponse(httpResponse, filmProvider.searchFilm(sanitise(term)));
    }

}
