package com.jbridgiee.films.server.controller;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static org.apache.commons.lang3.CharEncoding.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.data.result.Result;

/**
 *
 * @author josh.bridge
 */
public abstract class Controller {

    private final MediaType contentType;

    Controller(MediaType contentType) {
        this.contentType = contentType;
    }

    <R extends Result> String completeRequest(HttpServletResponse response, R result) {
        setHeader(response);

        return createResponse(result);
    }

    abstract <R extends Result> String createResponse(R result);

    String sanitise(String input) {
        try {
            return URLDecoder.decode(input, UTF_8).trim();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Unable to decode input", e);
        }
    }

    private void setHeader(HttpServletResponse response) {
        response.setHeader(CONTENT_TYPE, contentType.toString());
    }
}
