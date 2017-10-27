package server;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * @author josh.bridge
 */
@JacksonXmlRootElement(localName = "film")
public class Film {

    private final int id;

    private final String title;

    private final int year;

    private final int runtime;


    public Film(int id, String title, int year, int runtime) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }

    public Film() {
        this.id = 0;
        this.title = "";
        this.year = 0;
        this.runtime = 0;
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

    public int getRuntime() {
        return runtime;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; " +
                "Title: " + title + "; " +
                "Year: " + year + "; " +
                "Duration " + runtime + " minutes;";
    }
}
