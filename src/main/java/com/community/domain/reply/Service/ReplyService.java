package com.community.domain.reply.Service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.community.domain.post.entity.Post;
import com.community.domain.post.service.PostService;
import com.community.domain.reply.dto.req.ReplyCreateReq;
import com.community.domain.reply.dto.req.ReplySearchCond;
import com.community.domain.reply.dto.res.ReplyRes;
import com.community.domain.reply.entity.Reply;
import com.community.domain.reply.exception.ReplyNotFoundException;
import com.community.domain.reply.repository.ReplyRepository;
import com.community.domain.user.entity.User;
import com.community.domain.user.service.UserService;
import com.community.global.config.security.dto.SessionUser;
import com.community.global.exception.CannotAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository repository;

	private final UserService userService;

	private final PostService postService;

	public Reply findById(Long replyId) {
		return repository.findById(replyId)
			.orElseThrow(ReplyNotFoundException::new);
	}

	public ReplyRes createReply(SessionUser sessionUser, ReplyCreateReq req) {
		User user = userService.findById(sessionUser.getId());
		Post post = postService.findById(req.postId());
		Reply parrentReply = null;
		if (req.parentId() != null) {
			parrentReply = findById(req.parentId());
		}

		long l = System.currentTimeMillis();

		Reply reply = Reply.builder()
			.user(user)
			.post(post)
			.content(req.content())
			.mills(l)
			.parrentReply(parrentReply)
			.build();

		Reply savedReply = repository.save(reply);
		return ReplyRes.from(reply);

	}

	private void checkAccess(SessionUser sessionUser, Reply reply) {
		if (!sessionUser.getId().equals(reply.getUser().getId())) {
			throw new CannotAccessException();
		}
	}

	public void deleteReply(Long replyId, SessionUser sessionUser) {
		Reply reply = findById(replyId);
		checkAccess(sessionUser, reply);
		repository.delete(reply);
	}

	public List<ReplyRes> getRepliesBySearchCond(ReplySearchCond searchCond) {
		List<Reply> replies = repository.getRepliesByPostIdAndLastSeenPath(
			searchCond.getPostId(), searchCond.getLastSeenPath(), Pageable.ofSize(searchCond.getLimit()));
		return replies.stream()
			.map(ReplyRes::from)
			.toList();
	}

}
