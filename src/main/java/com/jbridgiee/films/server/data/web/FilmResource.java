package com.jbridgiee.films.server.data.web;


import com.google.common.collect.Maps;
import com.jbridgiee.films.server.controller.FilmsController;
import com.jbridgiee.films.server.data.Film;
import org.springframework.hateoas.Link;

import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FilmResource extends Film {

    private final Map<String, FilmLink> _links;

    public FilmResource(Film film) {
        super(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getDescription());

        final Link self = getSelf(film.getId());

        this._links = Maps.newHashMap();

        this._links.put("link", getHref(self));
    }

    public Map<String, FilmLink> getLinks() {
        return _links;
    }

    private FilmLink getHref(Link self) {
        return new FilmLink(self.getRel(), self.getHref());
    }

    private Link getSelf(int id) {
        return linkTo(methodOn(FilmsController.class).details(id)).withSelfRel();
    }
}