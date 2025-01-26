package com.community.global.config.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

import com.community.global.config.security.handler.AuthenticationFailureCustomHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomFilterDsl extends AbstractHttpConfigurer<CustomFilterDsl, HttpSecurity> {

	private final AuthenticationFailureCustomHandler authenticationFailureCustomHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final BasicLoginFilter basicLoginFilter;

	@Override
	public void configure(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		basicLoginFilter.setFilterProcessesUrl("/api/login");
		basicLoginFilter.setAuthenticationManager(authenticationManager);
		basicLoginFilter.setAuthenticationFailureHandler(authenticationFailureCustomHandler);
		// http.addFilterBefore(basicLoginFilter, UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(jwtAuthenticationFilter, BasicLoginFilter.class);
	}

}
