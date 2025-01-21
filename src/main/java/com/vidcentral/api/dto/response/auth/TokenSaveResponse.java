package com.vidcentral.api.dto.response.auth;

import lombok.Builder;

@Builder
public record TokenSaveResponse(
	String refreshToken
) {
}
