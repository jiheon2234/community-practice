package com.community.domain.post.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.community.domain.post.dto.req.PostCreateReq;
import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.dto.req.PostUpdateReq;
import com.community.domain.supports.ControllerTestSupport;
import com.community.domain.supports.WithMockSessionUser;

class PostControllerTest extends ControllerTestSupport {

	@Test
	@DisplayName("계시글 생성 성공 케이스")
	@WithMockSessionUser(id = 100, email = "test@example.com")
	void test1() throws Exception {
		PostCreateReq req = new PostCreateReq("title", "content");

		mockMvc.perform(post("/api/post")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("로그인하지 않은 요청은 실패한다")
	@WithAnonymousUser
	void test2() throws Exception {
		PostCreateReq req = new PostCreateReq("title", "content");

		mockMvc.perform(post("/api/post")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@DisplayName("제목은 1자 이상이어야 한다")
	@WithMockSessionUser(id = 100, email = "test@example.com")
	void test3() throws Exception {
		PostCreateReq req = new PostCreateReq("", "content");

		mockMvc.perform(post("/api/post")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@DisplayName("내용은 1자 이상이어야 한다")
	@WithAnonymousUser
	void test4() throws Exception {
		PostCreateReq req = new PostCreateReq("title", "");

		mockMvc.perform(post("/api/post")
				.content(objectMapper.writeValueAsString(req))
				.contentType(APPLICATION_JSON)
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@DisplayName("계시글 삭제 성공 케이스")
	@WithMockSessionUser(id = 100, email = "test@example.com")
	void test5() throws Exception {

		mockMvc.perform(delete("/api/post/1")
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("post 리스트 조회 성공 케이스")
	void test6() throws Exception {
		PostSearchCond postSearchCond = new PostSearchCond("a", "a", 1L);
		mockMvc.perform(get("/api/post")
			).andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@DisplayName("post 업데이트 200")
	void test7() throws Exception {
		PostUpdateReq req = new PostUpdateReq("title", "content");

		mockMvc.perform(patch("/api/post/{postId}", 1L))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}