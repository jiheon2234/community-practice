package com.community.domain.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.domain.post.dto.req.PostCreateReq;
import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.dto.req.PostUpdateReq;
import com.community.domain.post.dto.res.PostRes;
import com.community.domain.post.dto.res.SimplePostRes;
import com.community.domain.post.service.PostService;
import com.community.global.config.security.dto.SessionUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

	private final PostService postService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("")
	public ResponseEntity<PostRes> createPost(
		@AuthenticationPrincipal SessionUser sessionUser,
		@RequestBody @Valid PostCreateReq postCreateReq
	) {
		PostRes postRes = postService.createPost(sessionUser, postCreateReq);

		return ResponseEntity.ok(postRes);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostRes> getPost(@PathVariable Long postId) {
		PostRes postRes = postService.getSinglePost(postId);
		return ResponseEntity.ok(postRes);
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<Void> updatePost(@PathVariable Long postId,
		@AuthenticationPrincipal SessionUser sessionUser,
		PostUpdateReq req
	) {
		postService.updatePost(postId, sessionUser, req);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@AuthenticationPrincipal SessionUser sessionUser,
		@PathVariable Long postId) {
		postService.deletePost(sessionUser, postId);

		return ResponseEntity.ok().build();
	}

	@GetMapping("")
	public ResponseEntity<Page<SimplePostRes>> getPostList(
		PostSearchCond searchCond,
		@PageableDefault(size = 20) Pageable pageable
	) {
		Page<SimplePostRes> posts = postService.getPostList(pageable, searchCond);
		return ResponseEntity.ok(posts);
	}

}

