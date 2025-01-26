package com.community.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.community.domain.user.dto.req.SignUpBasicReq;
import com.community.domain.user.dto.res.SignUpBasicRes;
import com.community.domain.user.entity.User;
import com.community.domain.user.exception.UserNotFoundException;
import com.community.domain.user.repository.UserRepository;
import com.community.global.config.security.Jwt.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final JwtService jwtService;

	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(UserNotFoundException::new);
	}

	public SignUpBasicRes signUp(SignUpBasicReq req) {
		User user = userMapper.toEntity(req);
		changePassword(user, user.getPassword());
		User savedUser = userRepository.save(user);
		log.info("User created = {}", user);

		String directLoginURL = "TODO!"; //TODO
		return new SignUpBasicRes(userMapper.toRes(savedUser), directLoginURL);
	}

	private void changePassword(User user, String password) {
		user.changePassword(passwordEncoder.encode(user.getPassword()));
	}

}
