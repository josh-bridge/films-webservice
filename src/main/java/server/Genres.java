package server;

/**
 *
 * @author josh.bridge
 */
public class Genres {

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
