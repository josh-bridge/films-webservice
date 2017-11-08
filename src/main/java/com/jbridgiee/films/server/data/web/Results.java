package com.jbridgiee.films.server.data.web;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "results")
public class Results {

    public static Results from(List<FilmResource> results) {
        return new Results(results);
    }

    @JacksonXmlElementWrapper(localName = "resources")
    @JacksonXmlProperty(localName = "resource")
    private final List<FilmResource> results;

    private Results(List<FilmResource> results) {
        this.results = results;
    }

    public List<FilmResource> getResults() {
        return results;
    }

}