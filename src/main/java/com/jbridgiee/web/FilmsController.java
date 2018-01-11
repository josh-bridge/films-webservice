package com.jbridgiee.web;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
import com.jbridgiee.model.access.FilmDAO;
import com.jbridgiee.model.Film;
import com.jbridgiee.web.response.FilmResource;
import com.jbridgiee.web.response.Results;

/**
 * The handler for all API calls
 *
 * Deals mainly in REST, although 'search' might not totally satisfy REST requirements.
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/films")
public class FilmsController {

    private final FilmDAO filmService;

    @Autowired
    public FilmsController(FilmDAO filmService) {
        this.filmService = filmService;
    }

    /**
     * Get all the films stored in the database/datastore.
     *
     * @return all films
     */
    @GetMapping
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(getResults(filmService.getAll()));
    }

    /**
     * Add a film into the database/datastore
     *
     * @param input the film to add
     * @return an http response with the uri of the new resource
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Film input) {
        filmService.create(input);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(input.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Get a resource from the database/datastore
     *
     * @param id the id of the film
     * @return the film, or a 404 response
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        return filmService.getById(id)
                .map(film -> ResponseEntity.ok(getResource(film)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update a film's fields
     *
     * Checks to make sure the ID in the film object is the same as the path variable.
     *
     * @param id the id of the film
     * @param input the film with new content
     * @return the updated resource or a bad request response
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetails(@PathVariable Long id, @RequestBody Film input) {
        if (filmService.getById(id).isPresent() && (input.getId() == null || input.getId().equals(id))) {
            if (input.getId() == null) {
                input.setId(id);
            }

            filmService.update(input);

            return ResponseEntity.ok(getResource(input));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a film from persistent storage
     *
     * @param id the id of the film to delete
     * @return an http OK response, or a 404 if the id does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return filmService.getById(id)
                .map(film -> {
                    filmService.delete(film);

                    return ResponseEntity.ok().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Search for films by its title
     *
     * @param term the search term
     * @return the matching films
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("title") String term) {
        return ResponseEntity.ok(getResults(filmService.search(decode(term))));
    }

    /**
     * Get a Film object as a web resource, with a self URI
     *
     * @param film the film to convert
     * @return the Film as a FilmResource
     */
    private FilmResource getResource(Film film) {
        return new FilmResource(film, getResourceURI(film.getId()));
    }

    /**
     * Convert a list of films to a Results web resource
     *
     * @param films the films to convert
     * @return the Results web resource
     */
    private Results getResults(Stream<Film> films) {
        final List<FilmResource> filmResources = Lists.newArrayList();

        films.forEach(film -> filmResources.add(getResource(film)));

        return Results.from(filmResources);
    }

    /**
     * Get the URI for a film web resource
     *
     * @param id the resource indicator
     * @return the resource URI
     */
    private Link getResourceURI(Long id) {
        return linkTo(methodOn(this.getClass()).details(id)).withSelfRel();
    }

    /**
     * Decode the web parameter input
     *
     * <p>
     *     e.g. 'gone+girl' turns into 'gone girl'
     * </p>
     *
     * @param input the value to decode
     * @return the decoded value
     */
    private String decode(String input) {
        try {
            return URLDecoder.decode(input, UTF_8).trim();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode input", e);
        }
    }
}
