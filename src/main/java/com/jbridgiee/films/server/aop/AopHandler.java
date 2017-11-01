package com.jbridgiee.films.server.aop;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.controller.Controller;

/**
 *
 * @author josh.bridge
 */
@Aspect
@Component
public class AopHandler {

    private static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";

    private static final String REQUEST_MAPPING_ANNOTATION = "annotation(" + REQUEST_MAPPING + ")";

    private static final String AUTO_CONTENT_TYPE = "com.jbridgiee.films.server.aop.annotations.AutoContentType";

    private static final String AUTO_CONTENT_TYPE_ANNOTATION = "annotation(" + AUTO_CONTENT_TYPE + ")";

    private static final String HTTP_RESPONSE = "javax.servlet.http.HttpServletResponse";

    private static final String HTTP_RESPONSE_PARAM = "execution(* com.jbridgiee.films.server.controller.FilmController.*(.., " + HTTP_RESPONSE + ", ..))";

    @Around("@annotation(com.jbridgiee.films.server.aop.annotations.LogResponseTime)")
    public Object printResponseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object returnedValue = runMethod(joinPoint);

        System.out.println(System.currentTimeMillis() - start + "ms");

        return returnedValue;
    }

    @Before("@" + AUTO_CONTENT_TYPE_ANNOTATION + " && @" + REQUEST_MAPPING_ANNOTATION + " && " + HTTP_RESPONSE_PARAM + " && args(response)")
    public void setHeader(JoinPoint joinPoint, HttpServletResponse response) throws Throwable {
        final Object target = joinPoint.getTarget();

        if (target instanceof Controller) {
            ((Controller) target).setHeader(response);
        }

        System.out.println("Set content-type: " + response.getHeader(CONTENT_TYPE));
    }

    private Object runMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }
}
