package com.oauth.springoauth.api.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oauth.springoauth.api.oauth.provider.Oauth2ProviderProperties;
import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;

public interface Oauth2ClientService {

	String requestAccessToken(String code, Oauth2ProviderProperties provider) throws JsonProcessingException;

	Oauth2MemberProfile requestMemberProfile(String accessToken, String providerName,
		Oauth2ProviderProperties provider) throws
		JsonProcessingException;
}
