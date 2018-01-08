package com.jbridgiee.web.response;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.collect.Maps;
import com.jbridgiee.data.model.Film;
import com.jbridgiee.web.FilmsController;

@JacksonXmlRootElement(localName = "film")
public class FilmResource extends Film {

    private static Link getSelf(Long id) {
        return linkTo(methodOn(FilmsController.class).details(id)).withSelfRel();
    }

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(isAttribute = true)
    private final Map<String, FilmLink> links;

    public FilmResource(Film film) {
        super(film.getId(), film.getTitle(), film.getYear(), film.getDirector(), film.getStars(), film.getReview());

        this.links = Maps.newHashMap();

        final Link self = getSelf(film.getId());
        this.links.put(self.getRel(), new FilmLink(self.getHref()));
    }

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
