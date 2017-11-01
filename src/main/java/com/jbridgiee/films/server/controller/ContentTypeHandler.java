package com.jbridgiee.films.server.controller;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author josh.bridge
 */
@Aspect
@Component
public class ContentTypeHandler {

    private static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";

    private static final String AUTO_CONTENT_TYPE = "com.jbridgiee.films.server.controller.AutoContentType";

    private static final String CONTROLLER = "com.jbridgiee.films.server.controller.Controller";

    private static final String HTTP_RESPONSE = "javax.servlet.http.HttpServletResponse";

    private static final String CONTROLLER_SERVLET_EXECUTION = "execution(* " + CONTROLLER + "+.*(.., @" + AUTO_CONTENT_TYPE + " (" + HTTP_RESPONSE + "), ..))";

    /**
     * Runs for any method in a {@link Controller} sub-class with
     * a @{@link RequestMapping} annotation, and an @{@link AutoContentType}
     * annotation on a {@link HttpServletResponse} parameter.
     *
     * <p>For example:</p>
     *
     * <pre>
     * {@code @RequestMapping("/example")
     * public String example(@AutoContentType HttpServletResponse httpResponse)}
     * </pre>
     *
     * @param joinPoint
     *              the executed method's context
     * @param response
     *              the servlet response to set the content-type on.
     *
     * @throws Throwable
     *              any error that could occur
     */
    @Before("@annotation(" + REQUEST_MAPPING + ")" + " && " + CONTROLLER_SERVLET_EXECUTION + " && args(response)")
    public void setHeader(JoinPoint joinPoint, HttpServletResponse response) throws Throwable {
        ((Controller) joinPoint.getTarget()).setHeader(response);
    }

}
