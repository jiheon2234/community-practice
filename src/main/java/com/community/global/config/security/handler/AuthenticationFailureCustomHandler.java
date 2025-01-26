package com.community.global.config.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
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
public class AuthenticationFailureCustomHandler implements AuthenticationFailureHandler {

	private static final String MESSAGE = "AUTHENTICATION_FAILED - Invalid credential";
	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
	private final ErrorResponseUtils errorResponseUtils;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		log.error("# Authentication failed: {}", exception.getMessage());
		log.error("authentication ", exception);
		errorResponseUtils.sendErrorResponse(response, MESSAGE, HTTP_STATUS);
	}

}
