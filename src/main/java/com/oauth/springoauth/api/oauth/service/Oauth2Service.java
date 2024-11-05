package com.oauth.springoauth.api.oauth.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oauth.springoauth.api.oauth.config.MemoryProviderRepository;
import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;
import com.oauth.springoauth.domain.member.MemberRepository;
import com.oauth.springoauth.domain.member.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

	private final MemberRepository memberRepository;
	private final MemoryProviderRepository memoryProviderRepository;
	private final Oauth2ClientService oauthClientService;
	private final MemberService memberService;

	public LoginResponse loginOrRegister(String authorizationCode, String providerName) throws JsonProcessingException {
		Oauth2Provider provider = memoryProviderRepository.findProvider(providerName);
		String oauthAccessToken = oauthClientService.requestAccessToken(authorizationCode, provider);
		Oauth2MemberProfile memberProfile = oauthClientService
			.requestMemberProfile(oauthAccessToken, providerName, provider);
		if (!memberRepository.existsByOauthId(memberProfile.getOauthId())) {
			memberService.registerWithOauth(memberProfile.getOauthId(), memberProfile.getEmail(),
				memberProfile.getName());
		}
		return memberService.loginWithOauth(memberProfile.getEmail());
	}
}
