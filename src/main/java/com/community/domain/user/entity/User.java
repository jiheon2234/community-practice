package com.community.domain.user.entity;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.community.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Comment("email")
	@Column(nullable = false, unique = true)
	private String email;

	@Comment("pw")
	private String password;

	@Comment("닉네임")
	@Column(nullable = false)
	private String nickname;

	@Convert(converter = UserRoleConverter.class)
	private List<UserRole> roles = new ArrayList<>(List.of(UserRole.USER));

	@Comment("로그인 방식")
	@Enumerated(STRING)
	@Column(nullable = false)
	private Provider provider;

	private String sub;

	@Builder
	public User(String email, String password, String nickname, List<UserRole> roles, Provider provider, String sub) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.roles = roles;
		this.provider = provider;
		this.sub = sub;
	}

	public void changePassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
			"email='" + email + '\'' +
			", nickname='" + nickname + '\'' +
			", roles=" + roles +
			", sub='" + sub + '\'' +
			", provider=" + provider +
			'}';
	}
}
