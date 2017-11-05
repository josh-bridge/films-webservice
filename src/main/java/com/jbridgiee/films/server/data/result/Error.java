package com.jbridgiee.films.server.data.result;

/**
 *
 * @author josh.bridge
 */
public class Error<T extends Exception> extends Result {

    private final String errorMessage;

    private final T error;

    Error(String errorMessage, T error) {
        super(false);
        this.errorMessage = errorMessage;
        this.error = error;
    }

    Error(T error) {
        super(false);
        this.errorMessage = error.getMessage();
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getError() {
        return error;
    }
}
