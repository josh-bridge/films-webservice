package com.jbridgiee.films.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * @author josh.bridge
 */
@Aspect
@Component
public class LoggerHandler {

    @Around("@annotation(com.jbridgiee.films.server.aop.annotations.LogResponseTime)")
    public Object printResponseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object returnedValue = runMethod(joinPoint);

        System.out.println(System.currentTimeMillis() - start + "ms");

        return returnedValue;
    }

    private Object runMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed(joinPoint.getArgs());
    }

}
