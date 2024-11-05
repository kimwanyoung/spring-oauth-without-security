package com.oauth.springoauth.api.config;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.Oauth2Attributes;

public class MemoryProviderRepository {

	private final Map<Oauth2Attributes, Oauth2Provider> providers;

	public MemoryProviderRepository(Map<Oauth2Attributes, Oauth2Provider> providers) {
		this.providers = new HashMap<>(providers);
	}

	public Oauth2Provider findProvider(Oauth2Attributes providerType) {
		return providers.get(providerType);
	}
}
