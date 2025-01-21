package com.vidcentral.api.dto.response;

import lombok.Builder;

@Builder
public record MemberInfoResponse(
	String nickname,
	String profileImage
) {
}
