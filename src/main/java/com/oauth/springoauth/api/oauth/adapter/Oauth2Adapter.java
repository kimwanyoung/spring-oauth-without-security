package com.oauth.springoauth.api.oauth.adapter;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.config.Oauth2Properties;
import com.oauth.springoauth.api.oauth.provider.Oauth2ProviderProperties;
import com.oauth.springoauth.api.oauth.service.Oauth2ProviderType;

public class Oauth2Adapter {

	private Oauth2Adapter() {
	}

	public static Map<Oauth2ProviderType, Oauth2ProviderProperties> getOauth2Provider(Oauth2Properties properties) {
		Map<Oauth2ProviderType, Oauth2ProviderProperties> providers = new HashMap<>();
		properties.getUser().forEach((key, value) -> {
			Oauth2ProviderType providerType = Oauth2ProviderType.of(key);
			providers.put(providerType, new Oauth2ProviderProperties(value, properties.getProvider().get(key)));
		});
		return providers;
	}
}