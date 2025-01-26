package com.community.global.config.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.community.global.config.security.Jwt.JwtService;
import com.community.global.config.security.dto.CustomUserPrincipal;
import com.community.global.config.security.filter.dto.LoginDto;
import com.community.global.utils.ObjectMapperUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BasicLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final ObjectMapperUtils objectMapper;
	private final JwtService jwtService;

	@Autowired
	@Lazy
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		LoginDto loginDto = objectMapper.toDto(request, LoginDto.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
			loginDto.email(), loginDto.password());
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		CustomUserPrincipal principal = (CustomUserPrincipal)authResult.getPrincipal();

		String accessToken = createAccessToken(principal);

		response.setHeader(HttpHeaders.AUTHORIZATION,
			jwtService.getProperties().getPrefix() + accessToken);
		log.info(accessToken);

	}

	private String createAccessToken(CustomUserPrincipal principal) {
		return jwtService.generateAccessToken(
			principal.getId(),
			principal.getUsername(),
			principal.getAuthorities()
		);
	}
}
