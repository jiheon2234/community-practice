package com.community.global.config.security.Jwt;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {

	private final JwtProperties jwtProperties;

	@Bean
	public SecretKey secretKey() {
		val secret = jwtProperties.getSecretKey().getBytes();
		// new SecretKeySpec(secret, MacAlgorithm.HS256.getName())
		return new SecretKeySpec(secret, "HmacSHA256");

	}

	@Bean
	public JwtEncoder jwtEncoder() {
		val secretKey = secretKey().getEncoded();
		JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(secretKey);
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withSecretKey(secretKey()).build();
	}
}
