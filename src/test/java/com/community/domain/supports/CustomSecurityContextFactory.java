package com.community.domain.supports;

import java.util.Collections;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.community.global.config.security.dto.SessionUser;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithMockSessionUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockSessionUser annotation) {
		SessionUser sessionUser = SessionUser.of(annotation.id(), annotation.email(), Collections.emptyList());

		// UsernamePasswordAuthenticationToken authentication =
		// 	new UsernamePasswordAuthenticationToken(sessionUser, null, sessionUser.getAuthorities());

		// SecurityContext에 인증 객체 설정
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(sessionUser);
		return context;
	}
}
