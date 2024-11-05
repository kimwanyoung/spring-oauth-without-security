package com.oauth.springoauth.api.oauth.adapter;

import java.util.HashMap;
import java.util.Map;

import com.oauth.springoauth.api.oauth.config.Oauth2Properties;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;

public class Oauth2Adapter {

	private Oauth2Adapter() {
	}

	public static Map<String, Oauth2Provider> getOauth2Provider(Oauth2Properties properties) {
		Map<String, Oauth2Provider> providers = new HashMap<>();
		properties.getUser().forEach((key, value) ->
			providers.put(key, new Oauth2Provider(value, properties.getProvider().get(key))));
		return providers;
	}
}