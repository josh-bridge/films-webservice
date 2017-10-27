package server.result;

/**
 *
 * @author josh.bridge
 */
public class Data<T> extends Result {

    private final boolean success = true;

    // @JacksonXmlProperty(localName = "film")
    private final T data;

    public Data(T data) {
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public T getData() {
        return data;
    }

}
