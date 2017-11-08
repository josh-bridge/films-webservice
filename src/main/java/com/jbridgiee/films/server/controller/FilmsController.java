package com.jbridgiee.films.server.controller;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.FilmInfo;

@RestController
@RequestMapping("/films")
public class FilmsController {

    private final FilmInfo filmService;

    @Autowired
    public FilmsController(FilmInfo filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<?> all() {
        final List<FilmResource> filmResources = Lists.newArrayList();

        filmService.listFilm().forEach(film -> filmResources.add(new FilmResource(film)));

        return ResponseEntity.ok(filmResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable int id) {
        return filmService.getById(id)
                .map(film -> ResponseEntity.ok(new FilmResource(film)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Film input) {
        final Film result = filmService.addFilm(input);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Film input) {
        filmService.updateFilm(input);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAll(@RequestParam("q") String term) {
        final List<FilmResource> filmResources = Lists.newArrayList();

        filmService.searchFilm(sanitise(term)).forEach(film -> filmResources.add(new FilmResource(film)));

        return ResponseEntity.ok(filmResources);
    }

    @GetMapping("/search/{field}")
    public ResponseEntity<?> searchField(@PathVariable String field, @RequestParam("q") String term) {
        final List<FilmResource> filmResources = Lists.newArrayList();

        filmService.searchFilms(sanitise(field), sanitise(term)).forEach(film -> filmResources.add(new FilmResource(film)));

        return ResponseEntity.ok(filmResources);
    }

    private String sanitise(String input) {
        try {
            return URLDecoder.decode(input, UTF_8).trim();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode input", e);
        }
    }

    class FilmResource extends Film {

        private final Map<String, Map<String, String>> _links;

        FilmResource(Film film) {
            super(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getDescription());

            final Link self = getSelf(film.getId());

            this._links = Maps.newHashMap();

            this._links.put(self.getRel(), getHref(self));
        }

        public Map<String, Map<String, String>> getLinks() {
            return _links;
        }

        private HashMap<String, String> getHref(Link self) {
            final HashMap<String, String> href = Maps.newHashMap();

            href.put("href", self.getHref());

            return href;
        }

        private Link getSelf(int id) {
            return linkTo(methodOn(FilmsController.class).details(id)).withSelfRel();
        }
    }
}
