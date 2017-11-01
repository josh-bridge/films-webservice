package com.jbridgiee.films.server.aop;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

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
    public Object printResponseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object returnedValue = runMethod(joinPoint);

        System.out.println(System.currentTimeMillis() - start + "ms");

        return returnedValue;
    }

    // @Around("@annotation(com.jbridgiee.films.server.aop.annotations.AutoContentType)")
    // public Object setHeader(ProceedingJoinPoint pjp) throws Throwable {
    //     // final MediaType contentType = ((FilmController) pjp.getTarget()).getContentType();
    //     //
    //     // final Optional<Object> httpResponse = getHttpResponse(pjp.getArgs());
    //     //
    //     // if (httpResponse.isPresent()) {
    //     //     final HttpServletResponse httpServletResponse = (HttpServletResponse) httpResponse.get();
    //     //     httpServletResponse.setHeader(CONTENT_TYPE, contentType.toString());
    //     // }
    //
    //     return pjp.proceed(pjp.getArgs());
    // }

    @Around("@annotation(com.jbridgiee.films.server.aop.annotations.AServlet) && args(response)")
    public Object setHeader(ProceedingJoinPoint joinPoint, HttpServletResponse response) throws Throwable {
        response.setHeader(CONTENT_TYPE, getContentType(joinPoint).toString());

        return runMethod(joinPoint);
    }

    private MediaType getContentType(ProceedingJoinPoint joinPoint) {
        return ((FilmController) joinPoint.getTarget()).getContentType();
    }

    // private Annotation[][] getParams(ProceedingJoinPoint joinPoint) {
    //     return ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterAnnotations();
    // }
    //
    // private Optional<Object> getHttpResponse(Object[] args) {
    //     return Arrays.stream(args).filter(SERVLET_RESPONSE).findFirst();
    // }

    private Object runMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }
}
