package server;

import java.net.URL;
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

    private final URL thumb;

    private final Genres genres;

    public Film(int id, String title, Year release, int runtime, URL thumb, Genres genres) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.runtime = runtime;
        this.thumb = thumb;
        this.genres = genres;
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

    public URL getThumb() {
        return thumb;
    }

    public Genres getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + release + "; " +
                "Duration " + runtime + " minutes; " +
                "Thumb: + " + thumb + "; " +
                "Genre(s): " + genres + ";";
    }
}
