package com.community.global.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtils {
	private final ObjectMapper objectMapper;

	public <T> T toDto(HttpServletRequest request, Class<T> valueType) {
		try {
			return objectMapper.readValue(request.getInputStream(), valueType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T toDto(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String toStringValue(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}