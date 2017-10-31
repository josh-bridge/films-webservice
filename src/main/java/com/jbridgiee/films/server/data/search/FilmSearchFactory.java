package com.jbridgiee.films.server.data.search;

import java.util.List;

import com.google.common.collect.Lists;

/**
 *
 * @author josh.bridge
 */
public class FilmSearchFactory {

    private static final String TITLE = "title";

    private static final String DIRECTOR = "director";

    private static final String YEAR = "year";

    private static final String STARS = "stars";

    public static Search<String> searchTitle(String title) {
        return new Search<>(title.toUpperCase(), TITLE);
    }

    public static Search<Integer> searchYear(int year) {
        return new Search<>(year, YEAR);
    }

    public static Search<String> searchDirector(String director) {
        return new Search<>(director.toUpperCase(), DIRECTOR);
    }

    public static Search<String> searchStar(String star) {
        return new Search<>(star.toUpperCase(), STARS);
    }

    public static List<Search<?>> searchAll(String search) {
        final List<Search<?>> searches = Lists.newArrayList();

        searches.add(searchTitle(search));

        try {
            searches.add(searchYear(Integer.parseInt(search)));
        } catch (final NumberFormatException ignored) {}

        searches.add(searchDirector(search));
        searches.add(searchStar(search));

        return searches;
    }

}
