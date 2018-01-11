package com.jbridgiee.web.response;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.collect.Maps;
import com.jbridgiee.model.Film;

/**
 * A web resource version of the plain Film object
 *
 * @author josh.bridge
 */
@JacksonXmlRootElement(localName = "film")
public class FilmResource extends Film {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(isAttribute = true)
    private final Map<String, FilmLink> links;

    public FilmResource(Film film, Link... links) {
        super(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getReview());

        this.links = Maps.newHashMap();
        for (final Link link : links) {
            this.links.put(link.getRel(), new FilmLink(link.getHref()));
        }
    }

    /**
     * A custom representation of a URI for correct layout after FilmResource serialization
     */
    public class FilmLink {

        @JacksonXmlProperty(isAttribute = true)
        private final String href;

        FilmLink(String link) {
            this.href = link;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }
}
