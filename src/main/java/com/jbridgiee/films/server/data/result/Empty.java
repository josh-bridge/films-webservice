package com.jbridgiee.films.server.data.result;

/**
 *
 * @author josh.bridge
 */
public class Empty extends Result {

    private static final Empty INSTANCE = new Empty();

    @SuppressWarnings("unchecked")
    static Result result() {
        return INSTANCE;
    }

    private Empty() {
        super(false);
    }

    @Override
    public String toString() {
        return "No data.";
    }
}
