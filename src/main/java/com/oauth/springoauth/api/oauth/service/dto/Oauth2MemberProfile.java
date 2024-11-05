package com.oauth.springoauth.api.oauth.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Oauth2MemberProfile {
	private String oauthId;
	private String email;
	private String name;
}
