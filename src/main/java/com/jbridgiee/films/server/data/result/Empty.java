package com.jbridgiee.films.server.data.result;

/**
 *
 * @author josh.bridge
 */
public class Empty<T> extends Result<T> {

    private static final Empty<Object> INSTANCE = new Empty<>();

    @SuppressWarnings("unchecked")
    static <T> Result<T> result() {
        return (Result<T>) INSTANCE;
    }

    private Empty() {
        super(false);
    }

    @Override
    public T getData() {
        return null;
    }

    @Override
    public String toString() {
        return "No data.";
    }
}
