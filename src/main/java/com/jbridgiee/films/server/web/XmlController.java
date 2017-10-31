package com.jbridgiee.films.server.web;

import static com.google.common.net.MediaType.APPLICATION_XML_UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.jbridgiee.films.server.data.FilmInfo;
import com.jbridgiee.films.server.result.Result;

/**
 *
 * @author josh.bridge
 */
@RestController
@RequestMapping("/xml")
public class XmlController extends FilmController {

    private final XmlMapper xmlMapper;

    @Autowired
    public XmlController(FilmInfo filmProvider) {
        super(filmProvider, APPLICATION_XML_UTF_8);
        this.xmlMapper = new XmlMapper();
    }

    @Override
    <R extends Result> String createResponse(R result) {
        final StringBuilder builder = new StringBuilder();

        final ObjectWriter writer = xmlMapper.writer().withRootName("result");
        try {
            builder.append(writer.writeValueAsString(result));
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
