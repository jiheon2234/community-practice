package com.community.global.config.security.Jwt;

import java.time.Instant;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
	@Getter
	private final JwtProperties properties;
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;

	public Jwt decodeAccessToken(String token) {
		return jwtDecoder.decode(token);
	}

	public String generateAccessToken(Long id, String subject, Collection<? extends GrantedAuthority> authorities) {
		val expiration = getAccessExpiration();
		val header = JwsHeader.with(MacAlgorithm.HS256).type("jwt").build();
		val clams = JwtClaimsSet.builder()
			.subject(subject)
			.claim("id", id)
			.claim("authorities", authorities)
			.expiresAt(expiration)
			.build();

		return jwtEncoder.encode(JwtEncoderParameters.from(header, clams)).getTokenValue();
	}

	private Instant getAccessExpiration() {
		return addExpirationTime(properties.getAccessTokenValidityInSeconds());
	}

	private Instant addExpirationTime(int expirationSeconds) {
		return Instant.now().plusSeconds(expirationSeconds);
	}

}

