package com.jbridgiee.films.server;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 *
 * @author josh.bridge
 */
public class Film {

    private final int id;

    private final String title;

    private final int year;

    private final String director;

    private final List<String> stars;

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
        return "Film [Id: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + year + "; " +
                "Director: " + director + "; " +
                "Stars: " + String.join(", ", stars) + "; " +
                "Description: \"" + description + "\";]";
    }

    public static boolean equals(Film film, Film film2) {
        return film != null && film2 != null && EqualsBuilder.reflectionEquals(film, film2);
    }
}
