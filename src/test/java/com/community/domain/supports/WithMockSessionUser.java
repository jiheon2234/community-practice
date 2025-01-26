package com.community.domain.supports;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@WithSecurityContext(factory = CustomSecurityContextFactory.class)
public @interface WithMockSessionUser {

	long id() default 1L;

	String email() default "jiheon2234@gmail.com";
}
