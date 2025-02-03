package com.vidcentral.api.dto.response.viewHistory;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "비디오 시청 기록 목록 응답")
public record ViewHistoryListResponse(
	@Schema(description = "비디오 제목", example = "videoTitle")
	String title,

	@Schema(description = "비디오 설명", example = "videoDescription")
	String description,

	@Schema(description = "비디오 URL", example = "https://example.com/video1")
	String videoURL,

	@Schema(description = "비디오 조회수", example = "1500")
	Long views,

	@Schema(description = "비디오 시청 시간", example = "2023-02-03T10:15:30")
	LocalDateTime watchedAt
) {
}
