package com.jbridgiee.films.server.controller;

import static com.google.common.net.MediaType.JSON_UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.GsonBuilder;

import com.jbridgiee.films.server.data.access.FilmInfo;
import com.jbridgiee.films.server.data.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/json")
public class JsonController extends FilmController {

    private final GsonBuilder jsonBuilder;

    @Autowired
    public JsonController(FilmInfo filmProvider) {
        super(JSON_UTF_8, filmProvider);
        this.jsonBuilder = new GsonBuilder();
    }

    @Override
    <R extends Result> String createResponse(R result) {
        return jsonBuilder.create().toJson(result);
    }

}
