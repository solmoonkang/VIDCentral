package com.vidcentral.api.dto.response.video;

import lombok.Builder;

@Builder
public record VideoListResponse(
	String nickname,
	String title,
	String videoURL,
	Long views
) {
}
