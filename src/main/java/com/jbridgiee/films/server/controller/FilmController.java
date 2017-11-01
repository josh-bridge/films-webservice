package com.jbridgiee.films.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.data.access.FilmInfo;

public abstract class FilmController extends Controller {

    private final FilmInfo filmProvider;

    FilmController(MediaType contentType, FilmInfo filmProvider) {
        super(contentType);
        this.filmProvider = filmProvider;
    }

    @RequestMapping("/details/{id}")
    public String details(@AutoContentType HttpServletResponse httpResponse, @PathVariable int id) {
        return createResponse(filmProvider.getById(id));
    }

    @RequestMapping("/all")
    public String all(@AutoContentType HttpServletResponse httpResponse) {
        return createResponse(filmProvider.listFilm());
    }

    @RequestMapping("/search/{term}")
    public String search(@AutoContentType HttpServletResponse httpResponse, @PathVariable String term) {
        return createResponse(filmProvider.searchFilm(sanitise(term)));
    }

}
