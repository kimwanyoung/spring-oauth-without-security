package com.oauth.springoauth.api.oauth.config;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.Oauth2ProviderType;

public class MemoryProviderRepository {

	private final Map<Oauth2ProviderType, Oauth2Provider> providers;

	public MemoryProviderRepository(Map<Oauth2ProviderType, Oauth2Provider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public Oauth2Provider findProvider(Oauth2ProviderType type) {
		return providers.get(type);
	}
}
