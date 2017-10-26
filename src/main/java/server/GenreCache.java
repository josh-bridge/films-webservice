package server;

import java.util.Optional;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 *
 * @author josh.bridge
 */
public class GenreCache {

    private final Cache<Integer, String> cache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build();

    public Optional<String> getGenreById(int id) {
        //cache.get()

        return Optional.empty();
    }
}
