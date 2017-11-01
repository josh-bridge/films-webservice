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
    public String details(HttpServletResponse httpResponse, @PathVariable int id) {
        return completeRequest(httpResponse, filmProvider.getById(id));
    }

    @RequestMapping("/all")
    public String all(HttpServletResponse httpResponse) {
        return completeRequest(httpResponse, filmProvider.listFilm());
    }

    @RequestMapping("/search/{term}")
    public String search(HttpServletResponse httpResponse, @PathVariable String term) {
        return completeRequest(httpResponse, filmProvider.searchFilm(sanitise(term)));
    }

}
