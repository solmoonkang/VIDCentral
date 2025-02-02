package com.vidcentral.api.dto.response.member;

import lombok.Builder;

@Builder
public record MemberInfoResponse(
	String nickname,
	String introduce
) {
}
