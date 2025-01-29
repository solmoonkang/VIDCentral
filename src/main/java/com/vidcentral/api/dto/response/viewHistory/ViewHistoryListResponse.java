package com.vidcentral.api.dto.response.viewHistory;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ViewHistoryListResponse(
	String title,
	String description,
	String videoURL,
	Long views,
	LocalDateTime watchedAt
) {
}
