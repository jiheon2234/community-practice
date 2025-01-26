package com.community.global.config.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.community.global.config.security.Jwt.JwtService;
import com.community.global.config.security.dto.SessionUser;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * JWT를 사용해서 인증권한을 contextHolder에 넣어주는 필터
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		val header = request.getHeader(HttpHeaders.AUTHORIZATION);
		return (StringUtil.isNullOrEmpty(header) || !header.startsWith(jwtService.getProperties().getPrefix()));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		val header = request.getHeader(HttpHeaders.AUTHORIZATION);
		val properties = jwtService.getProperties();
		val accessToken = header.substring(properties.getPrefixLength()).trim();
		SessionUser sessionUser = decodeAccessToken(accessToken);

		SecurityContextHolder.getContext().setAuthentication(sessionUser);

		filterChain.doFilter(request, response);
	}

	private SessionUser decodeAccessToken(String accessToken) {
		val jwt = jwtService.decodeAccessToken(accessToken);
		Long id = jwt.getClaim("id");
		String email = jwt.getSubject();
		List<Map<String, String>> authoritiesClaim = jwt.getClaim("authorities");
		List<GrantedAuthority> authorities = authoritiesClaim.stream()
			.map(auth -> new SimpleGrantedAuthority(auth.get("role")))
			.collect(Collectors.toList());

		return SessionUser.of(id, email, authorities);

	}

}
