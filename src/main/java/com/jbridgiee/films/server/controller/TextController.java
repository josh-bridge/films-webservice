package com.jbridgiee.films.server.controller;

import static com.google.common.net.MediaType.PLAIN_TEXT_UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jbridgiee.films.server.data.access.FilmInfo;
import com.jbridgiee.films.server.data.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/text")
public class TextController extends FilmController {

    @Autowired
    public TextController(FilmInfo filmProvider) {
        super(PLAIN_TEXT_UTF_8, filmProvider);
    }

    @Override
    <R extends Result> String createResponse(R result) {
        return result.toString();
    }

}
