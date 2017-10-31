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
public class AopHandler {

    @Around("@annotation(com.jbridgiee.films.server.aop.LogResponseTime)")
    public Object printResponseTime(ProceedingJoinPoint pjp) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object retVal = pjp.proceed(pjp.getArgs());

        System.out.println(System.currentTimeMillis() - start + "ms");

        return retVal;
    }
}
