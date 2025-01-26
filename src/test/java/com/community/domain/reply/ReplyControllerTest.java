package com.community.domain.reply;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.community.domain.supports.ControllerTestSupport;

class ReplyControllerTest extends ControllerTestSupport {

	@DisplayName("목록 가져오기 200")
	@Test
	public void test1() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/reply")
				.param("postId", "1"))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}

}