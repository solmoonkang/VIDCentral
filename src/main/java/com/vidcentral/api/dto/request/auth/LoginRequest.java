package com.vidcentral.api.dto.request.auth;

import lombok.Builder;

@Builder
public record LoginRequest(
	String email,
	String password
) {
}
