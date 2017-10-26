package server;

/**
 *
 * @author josh.bridge
 */
public class ContentWrapper<T> extends ResultWrapper {

    private final T content;

    public ContentWrapper(boolean result, T content) {
        super(result);
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
