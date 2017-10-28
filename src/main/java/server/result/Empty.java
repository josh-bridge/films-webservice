package server.result;

/**
 *
 * @author josh.bridge
 */
public class Empty<T> extends Result<T> {

    private static final Empty<Object> INSTANCE = new Empty<>();

    @SuppressWarnings("unchecked")
    public static <T> Result<T> result() {
        return (Result<T>) INSTANCE;
    }

    private final boolean success = false;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public T getData() throws IllegalStateException {
        throw new IllegalStateException("ResultWrapper.getData() cannot be called on an empty value");
    }

    @Override
    public String toString() {
        return "No data to show.";
    }
}
