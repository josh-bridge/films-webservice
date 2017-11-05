package com.jbridgiee.films.server.data.result;

/**
 *
 * @author josh.bridge
 */
public class Updated<T> extends Result {

    private final T info;

    public Updated(T info) {
        super(true);

        this.info = info;
    }

    public T getInfo() {
        return info;
    }
}
