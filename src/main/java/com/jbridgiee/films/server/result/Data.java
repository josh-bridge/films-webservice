package com.jbridgiee.films.server.result;

/**
 *
 * @author josh.bridge
 */
public class Data<T> extends Result {

    private final T data;

    public Data(T data) {
        super(true);
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

}
