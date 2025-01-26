package com.community.global.config.security;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.community.global.config.security.filter.CustomFilterDsl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {

	private final CustomFilterDsl customFilterDsl;

	private final AuthenticationEntryPoint authenticationEntryPoint;

	private final AccessDeniedHandler accessDeniedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.with(customFilterDsl, configure -> configure.configure(http))
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(s -> s.sessionCreationPolicy(STATELESS))
			.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
			.exceptionHandling(exception ->
				exception.authenticationEntryPoint(authenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler))
			.headers().frameOptions().disable();

		return http.build();

	}

	// private final ServletContext servletContext;
	//
	// @PostConstruct
	// public void logServletFilters() throws ServletException {
	// 	Map<String, ? extends FilterRegistration> filters = servletContext.getFilterRegistrations();
	// 	for (Map.Entry<String, ? extends FilterRegistration> entry : filters.entrySet()) {
	// 		System.out.println(
	// 			"Registered Servlet Filter: " + entry.getKey() + " -> " + entry.getValue().getClassName());
	// 	}
	// }
}
