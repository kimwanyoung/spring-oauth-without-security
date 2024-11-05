package com.oauth.springoauth.domain.member;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;
import com.oauth.springoauth.common.JwtHelper;
import com.oauth.springoauth.security.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtHelper jwtHelper;

	public LoginResponse loginWithOauth(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("가입된 유저를 찾을 수 없습니다."));
		String accessToken = jwtHelper.generateAccessToken(member);
		return new LoginResponse(accessToken);
	}

	public void registerWithOauth(String oauthId, String email, String name) {
		memberRepository.findByEmail(email)
			.ifPresentOrElse(
				savedMember -> savedMember.updateOauthId(oauthId),
				() -> {
					String password = passwordEncoder.encode(UUID.randomUUID().toString());
					Member oauthMember = Member.builder()
						.oauthId(oauthId)
						.email(email)
						.nickname(name)
						.password(password)
						.build();
					memberRepository.save(oauthMember);
				});
	}
}
