package com.jbridgiee.films.server.data.search;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author josh.bridge
 */
public class Search {

    private final String term;

    private final List<String> fields;

    Search(String term, List<String> fields) {
        this.term = term;
        this.fields = fields;
    }

    Search(String term, String... fields) {
        this(term, Arrays.asList(fields));
    }

    public String getTerm() {
        return term;
    }

    public List<String> getFields() {
        return fields;
    }
}
