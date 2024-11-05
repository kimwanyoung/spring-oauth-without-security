package com.oauth.springoauth.api.oauth.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauth.springoauth.api.oauth.config.MemoryProviderRepository;
import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;
import com.oauth.springoauth.common.JwtHelper;
import com.oauth.springoauth.domain.member.MemberRepository;
import com.oauth.springoauth.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;
	private final MemberRepository memberRepository;
	private final MemoryProviderRepository memoryProviderRepository;
	private final RestTemplate restTemplate;

	public LoginResponse loginOrRegister(String authorizationCode, String providerName) throws JsonProcessingException {
		Oauth2Provider provider = memoryProviderRepository.findProvider(providerName);
		String oauthAccessToken = requestAccessToken(authorizationCode, provider);
		Oauth2MemberProfile memberProfile = requestMemberProfile(oauthAccessToken, providerName, provider);
		return new LoginResponse("");
	}

	private String requestAccessToken(String code, Oauth2Provider provider) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setBasicAuth(provider.getClientId(), provider.getClientSecret());
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", code);
		body.add("client_id", provider.getClientId());
		body.add("client_secret", provider.getClientSecret());
		body.add("grant_type", "authorization_code");
		body.add("redirect_uri", provider.getRedirectUrl());

		RequestEntity<MultiValueMap<String, String>> request = RequestEntity
			.post(provider.getTokenUrl())
			.headers(headers)
			.body(body);

		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		return jsonNode.get("access_token").asText();
	}

	private Oauth2MemberProfile requestMemberProfile(
		String accessToken,
		String providerName,
		Oauth2Provider provider
	) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<Object> requestHeader = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			provider.getUserInfoUrl(),
			HttpMethod.GET,
			requestHeader,
			String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> attributes = mapper.readValue(response.getBody(), Map.class);
		return Oauth2Attributes.extract(providerName, attributes);
	}
}
