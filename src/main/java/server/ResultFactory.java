package server;

import org.springframework.stereotype.Component;

/**
 *
 * @author josh.bridge
 */
@Component
public class ResultFactory {

    private static final ResultWrapper EMPTY_RESULT = new ResultWrapper(false);

    public <C> ContentWrapper<C> create(C content) {
        return new ContentWrapper<>(true, content);
    }

    public <C> ResultWrapper createNullable(C content) {
        if (content == null) {
            return EMPTY_RESULT;
        }

        return new ContentWrapper<>(true, content);
    }

    public ResultWrapper emptyResult() {
        return EMPTY_RESULT;
    }

}
