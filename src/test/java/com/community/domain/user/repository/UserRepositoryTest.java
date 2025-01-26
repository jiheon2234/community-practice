package com.community.domain.user.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.community.domain.supports.RepositoryTestSupport;
import com.community.domain.user.entity.Provider;
import com.community.domain.user.entity.User;
import com.community.domain.user.entity.UserRole;

class UserRepositoryTest extends RepositoryTestSupport {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("email이 일치하는 유저를 조회한다")
	@Test
	void test1() {
		User user = createUser("jiheon2234@gmail.com", "password", "nickname", UserRole.USER);
		userRepository.save(user);

		User findUser = userRepository.findByEmail(user.getEmail()).get();
		assertThat(findUser)
			.isNotNull()
			.extracting("email")
			.isEqualTo(user.getEmail());
	}

	private User createUser(String email, String password, String nickname, UserRole role) {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.provider(Provider.BASIC)
			.roles(List.of(role))
			.build();
	}

}