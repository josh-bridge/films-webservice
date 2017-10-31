package com.jbridgiee.films.server.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.ProceedingJoinPoint;

import com.jbridgiee.films.server.aop.AopHandler;

/**
 * Connects to {@link AopHandler#printResponseTime(ProceedingJoinPoint)}
 *
 * @author josh.bridge
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogResponseTime {

}
