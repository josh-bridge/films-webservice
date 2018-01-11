package com.jbridgiee.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * POJO for film data
 *
 * @author josh.bridge
 */
@Entity(name = "films")
public class Film implements Serializable {

    @Id
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    @Index
    private String title;

    private int year;

    @Index
    private String director;

    private String stars;

    private String review;

    public Film(Long id, String title, int year, String director, String stars, String review) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.stars = stars;
        this.review = review;
    }

    /**
     * Constructor required for Objectify
     */
    protected Film() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public String getStars() {
        return stars;
    }

    public String getReview() {
        return review;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
