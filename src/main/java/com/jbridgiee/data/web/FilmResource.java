package com.jbridgiee.data.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.hateoas.Link;

import com.google.common.collect.Maps;
import com.jbridgiee.web.FilmsController;
import com.jbridgiee.data.model.Film;

public class FilmResource extends Film {

    private final Map<String, String> links;

    public FilmResource(Film film) {
        super(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getReview());

        final Link self = getSelf(film.getId());

        this.links = Maps.newHashMap();

        this.links.put("self", self.getHref());
    }

    public Map<String, String> getLinks() {
        return links;
    }

    private Link getSelf(Long id) {
        return linkTo(methodOn(FilmsController.class).details(id)).withSelfRel();
    }
}
