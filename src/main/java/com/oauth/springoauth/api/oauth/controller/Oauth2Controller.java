package com.oauth.springoauth.api.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.springoauth.api.oauth.controller.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

	private final Oauth2Service oauth2Service;

	@GetMapping("/{provider}")
	public ResponseEntity<LoginResponse> loginOrRegister(
		@PathVariable String provider,
		@RequestBody String authorizationCode
	) {
		LoginResponse response = oauth2Service.loginOrRegister(authorizationCode, provider);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(response);
	}
}
