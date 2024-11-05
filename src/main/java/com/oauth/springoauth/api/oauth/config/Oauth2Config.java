package com.oauth.springoauth.api.oauth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
public class Oauth2Config {
	private final Oauth2Properties properties;

	public Oauth2Config(Oauth2Properties properties) {
		this.properties = properties;
	}
}
