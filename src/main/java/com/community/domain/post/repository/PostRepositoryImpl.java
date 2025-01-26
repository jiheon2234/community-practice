package com.community.domain.post.repository;

import static com.community.domain.post.entity.QPost.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import com.community.domain.post.dto.req.PostSearchCond;
import com.community.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Post> findPostsBySearchCond(Pageable pageable, PostSearchCond searchCond) {
		List<Post> posts = queryFactory
			.selectFrom(post)
			.where(
				userIdLike(searchCond.userId()),
				titleLike(searchCond.title()),
				contentLike(searchCond.content())
			)
			.orderBy(post.id.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> count = queryFactory
			.select(post.count())
			.from(post)
			.where(
				userIdLike(searchCond.userId()),
				titleLike(searchCond.title()),
				contentLike(searchCond.content())
			);

		return PageableExecutionUtils.getPage(posts, pageable, count::fetchOne);
	}

	private BooleanExpression titleLike(String title) {
		return StringUtils.hasText(title) ? post.title.contains(title) : null;
	}

	private BooleanExpression contentLike(String content) {
		return StringUtils.hasText(content) ? post.content.contains(content) : null;
	}

	private BooleanExpression userIdLike(Long userId) {
		return userId == null ? null : post.user.id.eq(userId);
	}
}


