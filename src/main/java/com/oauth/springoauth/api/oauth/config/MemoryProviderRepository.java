package com.oauth.springoauth.api.oauth.config;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.provider.Oauth2ProviderProperties;
import com.oauth.springoauth.api.oauth.service.Oauth2ProviderType;

public class MemoryProviderRepository {

	private final Map<Oauth2ProviderType, Oauth2ProviderProperties> providers;

	public MemoryProviderRepository(Map<Oauth2ProviderType, Oauth2ProviderProperties> providers) {
		this.providers = new HashMap<>(providers);
	}

	public Oauth2ProviderProperties findProvider(Oauth2ProviderType type) {
		return providers.get(type);
	}
}
