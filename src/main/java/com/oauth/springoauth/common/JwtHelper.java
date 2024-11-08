package com.oauth.springoauth.common;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.oauth.springoauth.domain.member.Member;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHelper {

	@Value("${jwt.token.secret.key}")
	private String tokenSecretKey;

	@Value("${jwt.token.expires.in}")
	private Long tokenExpiresIn;
	private Key key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(tokenSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(Member member) {
		return Jwts.builder()
			.setSubject(member.getId().toString())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + tokenExpiresIn))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public void validate(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
		} catch (JwtException e) {
			throw new JwtException(e.getMessage());
		}
	}

	public Long extractMemberId(String accessToken) {
		try {
			String subject = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
			return Long.valueOf(subject);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}
}
