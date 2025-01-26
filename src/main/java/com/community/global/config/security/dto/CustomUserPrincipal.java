package com.community.global.config.security.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.community.domain.user.entity.User;

import lombok.Getter;

public class CustomUserPrincipal implements OAuth2User, UserDetails {
	@Getter
	private final Long id;
	private final String email;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Map<String, Object> attributes;

	public CustomUserPrincipal(Long id, String email, String password, Collection<GrantedAuthority> authorities,
		Map<String, Object> attributes) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	public CustomUserPrincipal(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.authorities = user.getRoles().stream().
			map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
		this.attributes = null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return email;
	}
}
