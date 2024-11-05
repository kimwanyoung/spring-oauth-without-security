package com.oauth.springoauth.api.oauth.service;

import org.springframework.stereotype.Service;

import com.oauth.springoauth.api.oauth.config.MemoryProviderRepository;
import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;
import com.oauth.springoauth.common.JwtHelper;
import com.oauth.springoauth.domain.member.MemberRepository;
import com.oauth.springoauth.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;
	private final MemoryProviderRepository memoryProviderRepository;

	public LoginResponse loginOrRegister(String authorizationCode, String providerName) {
		return new LoginResponse("");
	}
}
