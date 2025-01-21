package com.vidcentral.api.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(
	String email,
	String password
) {
}
