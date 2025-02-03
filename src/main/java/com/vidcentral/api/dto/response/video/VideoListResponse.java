package com.vidcentral.api.dto.response.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "비디오 목록 응답")
public record VideoListResponse(
	@Schema(description = "비디오 업로더 사용자 닉네임", example = "uploaderMemberNickname")
	String nickname,

	@Schema(description = "비디오 제목", example = "videoTitle")
	String title,

	@Schema(description = "비디오 URL", example = "https://example.com/video1")
	String videoURL,

	@Schema(description = "비디오 조회수", example = "1500")
	Long views
) {
}
