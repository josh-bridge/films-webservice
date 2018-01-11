package com.jbridgiee.web.response;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Container for web results (of more than one resource)
 *
 * @author josh.bridge
 */
@JacksonXmlRootElement(localName = "results")
public class Results {

    /**
     * Static factory for web results
     *
     * @param results the list of results
     * @return the new results object
     */
    public static Results from(List<FilmResource> results) {
        return new Results(results);
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "film")
    private final List<FilmResource> results;

    private Results(List<FilmResource> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
