package com.vidcentral.api.application.auth;

import com.vidcentral.api.dto.request.TokenRequest;
import com.vidcentral.api.dto.response.LoginResponse;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationMapper {

	public static LoginResponse toLoginResponse(String accessToken, String refreshToken) {
		return LoginResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public static TokenRequest toTokenRequest(String accessToken, String refreshToken) {
		return TokenRequest.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
