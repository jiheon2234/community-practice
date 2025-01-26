package com.community.domain.reply.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;

import com.community.domain.BaseEntity;
import com.community.domain.post.entity.Post;
import com.community.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE id = ?")
public class Reply extends BaseEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "reply_seq_gen")
	@SequenceGenerator(name = "reply_seq_gen", sequenceName = "reply_seq", allocationSize = 100)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = LAZY)
	@Comment("상위 댓글 아이디")
	private Reply parrentReply;

	@Comment("내용")
	private String content;

	private String path;

	private long depth = 0L;

	private boolean isDeleted;

	@Builder
	private Reply(User user, Post post, Reply parrentReply, String content, long mills) {
		this.user = user;
		this.post = post;
		this.parrentReply = parrentReply;
		this.content = content;
		addPath(mills);
	}

	public void restore() {
		this.isDeleted = true;
	}

	private void addPath(long l) {
		String generatedPath = GeneratePathUtil.changeCurrentMillsToHANGUALPATH(l);
		String previousPath = parrentReply == null ? "" : parrentReply.getPath();
		this.path = previousPath + generatedPath;
		this.depth = parrentReply == null ? 0 : parrentReply.getDepth() + 1;

		if (this.depth > 50) {
			throw new IllegalStateException("varchar 255라서 50번이 최대일듯?");
		}
	}

}
