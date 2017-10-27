package server.result;

/**
 *
 * @author josh.bridge
 */
public abstract class Result<T> {

    @SuppressWarnings("unchecked")
    public static <T> Result<T> from(T data) {
        return new Data<>(data);
    }

    public static <T> Result<T> fromNullable(T data) {
        if (data == null) {
            return Empty.result();
        }

        return from(data);
    }

    public static <T> Result<T> emptyResult() {
        return Empty.result();
    }

    public abstract boolean isSuccess();

    public abstract T getData() throws IllegalStateException;

}
