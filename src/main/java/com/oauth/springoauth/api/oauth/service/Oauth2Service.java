package com.oauth.springoauth.api.oauth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oauth.springoauth.api.oauth.config.MemoryProviderRepository;
import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;
import com.oauth.springoauth.api.oauth.provider.Oauth2Provider;
import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;
import com.oauth.springoauth.common.JwtHelper;
import com.oauth.springoauth.domain.member.Member;
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
	private final OauthClientService oauthClientService;

	public LoginResponse loginOrRegister(String authorizationCode, String providerName) throws JsonProcessingException {
		Oauth2Provider provider = memoryProviderRepository.findProvider(providerName);
		String oauthAccessToken = oauthClientService.requestAccessToken(authorizationCode, provider);
		Oauth2MemberProfile memberProfile = oauthClientService
			.requestMemberProfile(oauthAccessToken, providerName, provider);
		if (!memberRepository.existsByOauthId(memberProfile.getOauthId())) {
			register(memberProfile);
		}
		return login(memberProfile);
	}

	private LoginResponse login(Oauth2MemberProfile memberProfile) {
		Member member = memberRepository.findByEmail(memberProfile.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("가입된 유저를 찾을 수 없습니다."));
		String accessToken = jwtHelper.generateAccessToken(member);
		return new LoginResponse(accessToken);
	}

	private void register(Oauth2MemberProfile memberProfile) {
		memberRepository.findByEmail(memberProfile.getEmail())
			.ifPresentOrElse(
				savedMember -> savedMember.updateOauthId(memberProfile.getOauthId()),
				() -> {
					String password = passwordEncoder.encode(UUID.randomUUID().toString());
					Member oauthMember = Member.builder()
						.oauthId(memberProfile.getOauthId())
						.email(memberProfile.getEmail())
						.nickname(memberProfile.getName())
						.password(password)
						.build();
					memberRepository.save(oauthMember);
				});
	}
}
