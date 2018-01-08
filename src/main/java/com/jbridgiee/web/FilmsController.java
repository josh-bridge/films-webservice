package com.jbridgiee.web;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.jbridgiee.data.access.FilmInfo;
import com.jbridgiee.data.model.Film;
import com.jbridgiee.web.response.FilmResource;
import com.jbridgiee.web.response.Results;

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

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Film input) {
        filmService.addFilm(input);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(input.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Film input) {
        if (!filmService.getById(input.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        filmService.updateFilm(input);

        return ResponseEntity.ok(new FilmResource(input));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        return filmService.getById(id)
                .map(film -> ResponseEntity.ok(new FilmResource(film)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetails(@PathVariable Long id, @RequestBody Film input) {
        if (filmService.getById(id).isPresent() && (input.getId() == null || input.getId().equals(id))) {
            if (input.getId() == null) {
                input.setId(id);
            }

            filmService.updateFilm(input);

            return ResponseEntity.ok(new FilmResource(input));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return filmService.getById(id)
                .map(film -> {
                    filmService.deleteFilm(film);

                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("title") String term) {
        final List<FilmResource> filmResources = Lists.newArrayList();

        filmService.searchFilms(decode(term)).forEach(film -> filmResources.add(new FilmResource(film)));

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
