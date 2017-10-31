package com.jbridgiee.films.server.data.search;

/**
 *
 * @author josh.bridge
 */
public class Search<T> {

    private final T term;

    private final String field;

    Search(T term, String field) {
        this.term = term;
        this.field = field;
    }

    public T getTerm() {
        return term;
    }

    public String getField() {
        return field;
    }

}
