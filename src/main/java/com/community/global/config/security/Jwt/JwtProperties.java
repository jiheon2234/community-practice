package com.community.global.config.security.Jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.community.global.config.property.YamlPropertySourceFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class JwtProperties {

	private String accessHeader;
	private String refreshHeader;
	private String prefix;
	private String secretKey;
	private Integer accessTokenValidityInSeconds;
	private Integer refreshTokenValidityInSeconds;

	public int getPrefixLength() {
		return prefix.length();
	}
}
