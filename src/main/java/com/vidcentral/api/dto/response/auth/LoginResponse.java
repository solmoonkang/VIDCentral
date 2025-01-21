package com.vidcentral.api.dto.response.auth;

import lombok.Builder;

@Builder
public record LoginResponse(
	String accessToken,
	String refreshToken
) {
}
