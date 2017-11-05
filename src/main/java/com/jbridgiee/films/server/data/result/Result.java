package com.jbridgiee.films.server.data.result;

import java.util.List;

/**
 *
 * @author josh.bridge
 */
public abstract class Result {

    public static Result emptyResult() {
        return Empty.result();
    }

    public static <T> Result fromData(T data) {
        return new Data<>(data);
    }

    public static <T> Result fromDataList(List<T> data) {
        return data == null || data.isEmpty() ? emptyResult() : fromData(data);
    }

    public static <T> Result fromUpdate(T info) {
        return new Updated<>(info);
    }

    public static <T extends Exception> Result fromError(String message, T error) {
        return new Error<>(message, error);
    }

    public static <T extends Exception> Result fromError(T error) {
        return new Error<>(error);
    }

    private final boolean success;

    Result(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
