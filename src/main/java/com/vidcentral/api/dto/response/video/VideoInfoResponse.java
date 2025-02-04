package com.vidcentral.api.dto.response.video;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "사용자 비디오 정보 응답")
public record VideoInfoResponse(
	@Schema(description = "사용자가 시청한 비디오 ID의 집합", example = "[1, 2, 3]")
	Set<Long> viewedVideoIds,

	@Schema(description = "사용자가 좋아요를 누른 비디오 ID의 집합", example = "[1, 2, 3]")
	Set<Long> likedVideoIds
) {
}
