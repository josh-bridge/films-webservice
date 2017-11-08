package com.jbridgiee.films.server.controller;

import com.google.common.collect.Lists;
import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.FilmInfo;
import com.jbridgiee.films.server.data.web.FilmResource;
import com.jbridgiee.films.server.data.web.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

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

        return ResponseEntity.ok(Results.from(filmResources));
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

        filmService.searchFilm(decode(term)).forEach(film -> filmResources.add(new FilmResource(film)));

        return ResponseEntity.ok(Results.from(filmResources));
    }

    @GetMapping("/search/{field}")
    public ResponseEntity<?> searchField(@PathVariable String field, @RequestParam("q") String term) {
        final List<FilmResource> filmResources = Lists.newArrayList();

        filmService.searchFilms(decode(field), decode(term)).forEach(film -> filmResources.add(new FilmResource(film)));

        return ResponseEntity.ok(Results.from(filmResources));
    }

    private String decode(String input) {
        try {
            return URLDecoder.decode(input, UTF_8).trim();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode input", e);
        }
    }
}
