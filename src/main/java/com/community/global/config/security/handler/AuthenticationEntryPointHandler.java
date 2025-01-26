package com.community.global.config.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.community.global.exception.ErrorResponseUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

	private static final String MESSAGE = "Authentication failed - CANNOT ACCESS";
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private final ErrorResponseUtils errorResponseUtils;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		errorResponseUtils.sendErrorResponse(response, MESSAGE, HTTP_STATUS);
	}
}
