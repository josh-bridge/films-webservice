package com.jbridgiee.films.server.data.web;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FilmLink {

    @JacksonXmlProperty(isAttribute = true)
    private final String rel;

    @JacksonXmlProperty(isAttribute = true)
    private final String href;

    public FilmLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }
}
