package com.oauth.springoauth.api.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;

public interface Oauth2ClientService {

	String requestAccessToken(String code, Oauth2Provider provider) throws JsonProcessingException;

	Oauth2MemberProfile requestMemberProfile(String accessToken, String providerName, Oauth2Provider provider) throws
		JsonProcessingException;
}
