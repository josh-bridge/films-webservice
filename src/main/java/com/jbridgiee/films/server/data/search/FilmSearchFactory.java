package com.jbridgiee.films.server.data.search;

/**
 *
 * @author josh.bridge
 */
public class FilmSearchFactory {

    private static final String TITLE = "title";

    private static final String DIRECTOR = "director";

    private static final String YEAR = "year";

    private static final String STARS = "stars";

    public static Search searchField(String field, String value) {
        return new Search(value, field);
    }

    public static Search searchTitle(String title) {
        return new Search(title.toUpperCase(), TITLE);
    }

    public static Search searchYear(int year) {
        return new Search(Integer.toString(year), YEAR);
    }

    public static Search searchDirector(String director) {
        return new Search(director.toUpperCase(), DIRECTOR);
    }

    public static Search searchStar(String star) {
        return new Search(star.toUpperCase(), STARS);
    }

    public static Search searchAll(String search, String... columns) {
        return new Search(search, columns);
    }

}
