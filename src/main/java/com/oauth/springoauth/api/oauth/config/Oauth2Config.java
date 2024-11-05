package com.oauth.springoauth.api.oauth.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oauth.springoauth.api.oauth.adapter.Oauth2Adapter;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;

@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
public class Oauth2Config {
	
	private final Oauth2Properties properties;

	public Oauth2Config(Oauth2Properties properties) {
		this.properties = properties;
	}

	@Bean
	public MemoryProviderRepository inMemoryProviderRepository() {
		Map<String, Oauth2Provider> providers = Oauth2Adapter.getOauth2Provider(properties);
		return new MemoryProviderRepository(providers);
	}
}
