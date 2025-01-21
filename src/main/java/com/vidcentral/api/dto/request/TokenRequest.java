package com.vidcentral.api.dto.request;

import lombok.Builder;

@Builder
public record TokenRequest(
	String accessToken,
	String refreshToken
) {
}
