package server;

import java.util.List;

/**
 *
 * @author josh.bridge
 */
public class Film {

    private final int id;

    private final String title;

    private final int year;

    private final String director;

    private final List<String> stars;

    private final String review;

    public Film(int id, String title, int year, String director, List<String> stars, String review) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.stars = stars;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getStars() {
        return stars;
    }

    public String getReview() {
        return review;
    }

    @Override
    public String toString() {
        return "Id: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + year + "; " +
                "Director " + director + "; " +
                "Stars: " + String.join(", ", stars) + "; " +
                "Review: \"" + review + "\";";
    }
}
