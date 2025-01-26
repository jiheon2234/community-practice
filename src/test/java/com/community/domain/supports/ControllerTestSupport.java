package com.community.domain.supports;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.community.domain.post.service.PostService;
import com.community.domain.reply.Service.ReplyService;
import com.community.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringBootTest
// @WebMvcTest(
// 	controllers = UserController.class
// )
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc()
@SpringBootTest
public abstract class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockitoBean
	private UserService userService;

	@MockitoBean
	private PostService postService;

	@MockitoBean
	private ReplyService replyService;

}
