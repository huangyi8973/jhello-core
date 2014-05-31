package com.jhello.core.annotations.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jhello.core.pub.HttpMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface At {
	String value() default "";
	HttpMethod method() default HttpMethod.GET;
}
