package com.jbridgiee.films.server.data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author josh.bridge
 */
public class Film implements Serializable {

    @JacksonXmlProperty(isAttribute = true)
    private final int id;

    @JacksonXmlProperty(isAttribute = true)
    private final String title;

    @JacksonXmlProperty(isAttribute = true)
    private final int year;

    @JacksonXmlProperty(isAttribute = true)
    private final String director;

    @JacksonXmlElementWrapper(localName = "stars")
    @JacksonXmlProperty(localName = "star")
    private final List<String> stars;

    @JacksonXmlProperty(isAttribute = true)
    private final String description;

    public Film(int id, String title, int year, String director, List<String> stars, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.stars = stars;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
