package com.oauth.springoauth.api.oauth.config;

import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.oauth.springoauth.api.oauth.adapter.Oauth2Adapter;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.Oauth2ClientService;
import com.oauth.springoauth.api.oauth.service.Oauth2ProviderType;
import com.oauth.springoauth.api.oauth.service.RestTemplateOauth2ClientService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(Oauth2Properties.class)
@RequiredArgsConstructor
public class Oauth2Config {

	private final Oauth2Properties properties;
	private final RestTemplate restTemplate;

	@Bean
	public MemoryProviderRepository memoryProviderRepository() {
		Map<Oauth2ProviderType, Oauth2Provider> providers = Oauth2Adapter.getOauth2Provider(properties);
		return new MemoryProviderRepository(providers);
	}

	@Bean
	public Oauth2ClientService oauthClientService() {
		return new RestTemplateOauth2ClientService(restTemplate);
	}
}
