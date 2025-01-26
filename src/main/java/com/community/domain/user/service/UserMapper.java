package com.community.domain.user.service;

import static com.community.domain.user.entity.UserRole.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.community.domain.user.dto.req.SignUpBasicReq;
import com.community.domain.user.dto.res.SimpleUserRes;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;

@Component
public class UserMapper {

	public User toEntity(SignUpBasicReq req) {
		return User.builder()
			.email(req.email())
			.password(req.password())
			.nickname(req.nickname())
			.roles(List.of(USER))
			.provider(Provider.BASIC)
			.build();
	}

	public SimpleUserRes toRes(User user) {
		return new SimpleUserRes(user.getId(), user.getProvider(), user.getNickname());
	}
}
