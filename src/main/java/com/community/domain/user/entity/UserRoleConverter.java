package com.community.domain.user.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<List<UserRole>, String> {

	@Override
	public String convertToDatabaseColumn(List<UserRole> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		}
		return attribute.stream()
			.map(UserRole::name)
			.collect(Collectors.joining(","));
	}

	@Override
	public List<UserRole> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isBlank()) {
			return List.of();
		}
		return Arrays.stream(dbData.split(","))
			.filter(role -> !role.isBlank())
			.map(UserRole::valueOf)
			.collect(Collectors.toList());
	}
}