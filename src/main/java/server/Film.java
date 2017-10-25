package server;

import java.time.Year;

/**
 *
 * @author josh.bridge
 */
public class Film {

    private final int id;

    private final String title;

    private final Year year;

    private final int duration;

    private final String thumbURL;

    private final Genres genres;

    public Film(int id, String title, Year year, int duration, String thumbURL, Genres genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.thumbURL = thumbURL;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Year getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public Genres getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + year + "; " +
                "Duration " + duration + " minutes; " +
                "Thumb: + " + thumbURL + "; " +
                "Genre(s): " + genres + ";";
    }
}
