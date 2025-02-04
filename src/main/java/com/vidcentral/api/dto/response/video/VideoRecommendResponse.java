package com.vidcentral.api.dto.response.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "비디오 추천 응답")
public record VideoRecommendResponse(
	@Schema(description = "비디오 ID", example = "1L")
	Long videoId,

	@Schema(description = "비디오 목록 응답", example = "memberNickname, videoTitle, videoURL, videoViews")
	VideoListResponse videoListResponse,

	@Schema(description = "추천 점수", example = "0.5")
	Double score
) {
}
