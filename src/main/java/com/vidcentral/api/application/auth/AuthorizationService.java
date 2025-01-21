package com.vidcentral.api.application.auth;

import org.springframework.stereotype.Service;

import com.vidcentral.api.dto.request.TokenRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

	private final JwtProviderService jwtProviderService;

	public TokenRequest issueServiceToken(String email, String nickname) {
		String accessToken = jwtProviderService.generateAccessToken(email, nickname);
		String refreshToken = jwtProviderService.generateRefreshToken(email);

		return AuthenticationMapper.toTokenRequest(accessToken, refreshToken);
	}
}
