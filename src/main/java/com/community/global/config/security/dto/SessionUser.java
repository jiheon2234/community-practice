package com.community.global.config.security.dto;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class SessionUser implements Authentication {
	private final Long id;
	private final String email;
	private final Collection<? extends GrantedAuthority> authorities;

	private SessionUser(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.authorities = authorities;
	}

	public static SessionUser of(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
		return new SessionUser(id, email, authorities);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		//
	}

	@Override
	public String getName() {
		return this.email;
	}
}
