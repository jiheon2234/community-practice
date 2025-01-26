package com.community.domain.user.dto.res;

import java.util.List;

import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.UserRole;

public record userRes(
	Long id, String email, String nickname, List<UserRole> roles, Provider provider
) {
}
