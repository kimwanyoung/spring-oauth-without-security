package com.oauth.springoauth.api.oauth.config;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;

public class MemoryProviderRepository {
	private final Map<String, Oauth2Provider> providers;

	public MemoryProviderRepository(Map<String, Oauth2Provider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public Oauth2Provider findProvider(String name) {
		return providers.get(name);
	}
}
