package com.community.domain.reply;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.domain.reply.Service.ReplyService;
import com.community.domain.reply.dto.req.ReplyCreateReq;
import com.community.domain.reply.dto.req.ReplySearchCond;
import com.community.domain.reply.dto.res.ReplyRes;
import com.community.global.config.security.dto.SessionUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {

	private final ReplyService service;

	@PostMapping("")
	public ResponseEntity<ReplyRes> createReply(@AuthenticationPrincipal SessionUser sessionUser,
		@RequestBody ReplyCreateReq req) {
		ReplyRes res = service.createReply(sessionUser, req);
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/{replyId}")
	public ResponseEntity<Void> deleteReply(
		@AuthenticationPrincipal SessionUser sessionUser,
		@PathVariable Long replyId
	) {
		service.deleteReply(service, replyId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("")
	public ResponseEntity<List<ReplyRes>> getRepliesBySearchCond(
		@ModelAttribute ReplySearchCond searchCond
	) {
		List<ReplyRes> replies = service.getRepliesBySearchCond(searchCond);
		return ResponseEntity.ok(replies);

	}

}
