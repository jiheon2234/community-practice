package com.community.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.domain.user.dto.req.SignUpBasicReq;
import com.community.domain.user.dto.res.SignUpBasicRes;
import com.community.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@PreAuthorize("isAnonymous()")
	@PostMapping("/sign-up")
	public ResponseEntity<SignUpBasicRes> signUp(@RequestBody @Valid SignUpBasicReq signUpBasicReq) {
		SignUpBasicRes res = userService.signUp(signUpBasicReq);
		return ResponseEntity.ok(res);
	}

	// @PreAuthorize("hasRole('ADMIN')")
	// @RequestMapping("/api/test")
	// public String test(@AuthenticationPrincipal SessionUser sessionUser) {
	// 	log.info("sessionuser = {}", sessionUser);
	//
	// 	return "sucess!!";
	// }

}
