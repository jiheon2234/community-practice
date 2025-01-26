package com.community.domain.user.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.community.domain.supports.ControllerTestSupport;
import com.community.domain.user.dto.req.SignUpBasicReq;

class UserControllerTest extends ControllerTestSupport {

	@Test
	@DisplayName("성공케이스")
	public void test() throws Exception {

		SignUpBasicReq req = new SignUpBasicReq("email@email.com", "name", "password");

		mockMvc.perform(post("/api/sign-up")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입시 아이디는 필수이다")
	public void test1() throws Exception {

		SignUpBasicReq req = new SignUpBasicReq(null, "name", "password");

		mockMvc.perform(post("/api/sign-up")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("회원가입시 닉네임은 필수이다")
	public void test2() throws Exception {

		SignUpBasicReq req = new SignUpBasicReq("email@email.com", "", "password");

		mockMvc.perform(post("/api/sign-up")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("회원가입시 비밀번호는 필수이다")
	public void test3() throws Exception {

		SignUpBasicReq req = new SignUpBasicReq("email@email.com", "nickname", "");

		mockMvc.perform(post("/api/sign-up")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isBadRequest());
	}

}