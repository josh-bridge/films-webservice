package com.jbridgiee.films.server.result;

import javax.annotation.Nullable;

/**
 *
 * @author josh.bridge
 */
public abstract class Result<T> {

    @SuppressWarnings("unchecked")
    public static <T> Result<T> from(T data) {
        return new Data<>(data);
    }

    public static <T> Result<T> emptyResult() {
        return Empty.result();
    }

    private final boolean success;

    Result(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public abstract T getData();

}
