package server;

import java.time.Year;

/**
 *
 * @author josh.bridge
 */
public class Film {

    private final int id;

    private final String title;

    private final Year release;

    private final int runtime;


    public Film(int id, String title, Year release, int runtime) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.runtime = runtime;
    }

    public Film() {
        this.id = 0;
        this.title = "";
        this.release = Year.now();
        this.runtime = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Year getRelease() {
        return release;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + release + "; " +
                "Duration " + runtime + " minutes;";
    }
}
