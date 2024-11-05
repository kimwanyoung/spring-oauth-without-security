package com.oauth.springoauth.api.oauth.service;

import java.util.Arrays;
import java.util.Map;

import com.oauth.springoauth.api.oauth.service.dto.Oauth2MemberProfile;

public enum Oauth2Attributes {
	GITHUB("github") {
		@Override
		public Oauth2MemberProfile of(Map<String, Object> attributes) {
			return Oauth2MemberProfile.builder()
				.oauthId(attributes.get("id").toString())
				.email((String)attributes.get("email"))
				.name((String)attributes.get("name"))
				.build();
		}
	},
	KAKAO("kakao") {
		@Override
		public Oauth2MemberProfile of(Map<String, Object> attributes) {
			Map<String, Object> containEmailResponse = ((Map<String, Object>)attributes.get(
				"kakao_account"));
			Map<String, Object> containNameResponse = ((Map<String, Object>)attributes.get(
				"properties"));
			return Oauth2MemberProfile.builder()
				.oauthId(attributes.get("id").toString())
				.email((String)containEmailResponse.get("email"))
				.name((String)containNameResponse.get("nickname"))
				.build();
		}
	},
	NAVER("naver") {
		@Override
		public Oauth2MemberProfile of(Map<String, Object> attributes) {
			Map<String, Object> response = (Map<String, Object>)attributes.get("response");
			return Oauth2MemberProfile.builder()
				.oauthId((String)response.get("id"))
				.email((String)response.get("email"))
				.name((String)response.get("name"))
				.build();
		}
	},
	GOOGLE("google") {
		@Override
		public Oauth2MemberProfile of(Map<String, Object> attributes) {
			return Oauth2MemberProfile.builder()
				.oauthId((String)attributes.get("id"))
				.email((String)attributes.get("email"))
				.name((String)attributes.get("name"))
				.build();
		}
	};

	private final String providerName;

	Oauth2Attributes(String name) {
		this.providerName = name;
	}

	public static Oauth2MemberProfile extract(String providerName, Map<String, Object> attributes) {
		return Arrays.stream(values())
			.filter(provider -> providerName.equals(provider.providerName))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.of(attributes);
	}

	public abstract Oauth2MemberProfile of(Map<String, Object> attributes);
}