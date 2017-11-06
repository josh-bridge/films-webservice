package com.jbridgiee.films.server.controller;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbridgiee.films.server.data.Film;
import com.jbridgiee.films.server.data.access.FilmInfo;

@RestController
@RequestMapping("/films")
public class FilmsController {

    private final FilmInfo filmService;

    @Autowired
    FilmsController(FilmInfo filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> all() {
        return ResponseEntity.ok(filmService.listFilm());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> details(@PathVariable int id) {
        return filmService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public String update() {
        return "unsupported";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Film>> searchAll(@RequestParam("q") String term) {
        return ResponseEntity.ok(filmService.searchFilm(sanitise(term)));
    }

    @GetMapping("/{field}/search")
    public ResponseEntity<List<Film>> searchField(@PathVariable String field, @RequestParam("q") String term) {
        return ResponseEntity.ok(filmService.searchFilms(sanitise(field), sanitise(term)));
    }

    @PostMapping
    public ResponseEntity<Film> add(@RequestBody Film input) {
        final Film result = filmService.addFilm(input);

        // final URI location = ServletUriComponentsBuilder
        //         .fromCurrentRequest().path("/{id}")
        //         .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.ok(result);
    }

    private String sanitise(String input) {
        try {
            return URLDecoder.decode(input, UTF_8).trim();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode input", e);
        }
    }

}
