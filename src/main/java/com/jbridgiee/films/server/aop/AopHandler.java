package com.jbridgiee.films.server.aop;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.google.common.net.MediaType;
import com.jbridgiee.films.server.controller.FilmController;

/**
 *
 * @author josh.bridge
 */
@Aspect
@Component
public class AopHandler {

    @Around("@annotation(com.jbridgiee.films.server.aop.annotations.LogResponseTime)")
    public Object printResponseTime(ProceedingJoinPoint pjp) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object retVal = pjp.proceed(pjp.getArgs());

        System.out.println(System.currentTimeMillis() - start + "ms");

        return retVal;
    }

    @Around("@annotation(com.jbridgiee.films.server.aop.annotations.AutoContentType)")
    public Object setHeader(ProceedingJoinPoint pjp) throws Throwable {
        final MediaType contentType = ((FilmController) pjp.getTarget()).getContentType();

        final Optional<Object> httpResponse = getHttpResponse(pjp.getArgs());

        if (httpResponse.isPresent()) {
            final HttpServletResponse httpServletResponse = (HttpServletResponse) httpResponse.get();
            httpServletResponse.setHeader(CONTENT_TYPE, contentType.toString());
        }

        return pjp.proceed(pjp.getArgs());
    }

    private Optional<Object> getHttpResponse(Object[] args) {
        return Arrays.stream(args).filter((arg) -> arg instanceof HttpServletResponse).findFirst();
    }
}
