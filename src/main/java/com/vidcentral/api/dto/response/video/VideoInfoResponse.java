package com.vidcentral.api.dto.response.video;

import lombok.Builder;

@Builder
public record VideoInfoResponse(
	String nickname,
	String title,
	String description,
	String videoURL
) {
}
