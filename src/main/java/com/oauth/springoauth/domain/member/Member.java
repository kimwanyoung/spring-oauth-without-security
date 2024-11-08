package com.oauth.springoauth.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	private String oauthId;
	private String email;
	private String password;
	private String nickname;

	@Builder
	public Member(Long id, String oauthId, String email, String password, String nickname) {
		this.id = id;
		this.oauthId = oauthId;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}

	public void updateOauthId(String oauthId) {
		this.oauthId = oauthId;
	}
}
