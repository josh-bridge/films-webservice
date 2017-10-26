package server;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 *
 * @author josh.bridge
 */
public class Genres {

    private static final Map<Integer, String> ALL = Maps.newHashMap();

    static {

    }

    private final Genre[] values;

    public Genres(Genre... values) {
        this.values = values;
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < values.length; i++) {
            result += values[i].getName();

            if (i + 1 < values.length) {
                result += ", ";
            }
        }

        return result;
    }
}
