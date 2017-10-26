package server;

import java.util.List;

/**
 *
 * @author josh.bridge
 */
public class ListUtil {

    public static String commaSeparatedList(List<?> values) {
        String result = "";

        for (int i = 0; i < values.size(); i++) {
            result += values.get(i).toString();

            if (i + 1 < values.size()) {
                result += ", ";
            }
        }

        return result;
    }

}
