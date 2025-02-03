package com.vidcentral.api.dto.request.video;

import java.util.Set;

import com.vidcentral.api.domain.video.entity.VideoTag;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "비디오 정보 수정 요청")
public record UpdateVideoRequest(
	@NotBlank(message = "비디오 제목은 필수 입력 항목입니다.")
	@Schema(description = "비디오 제목", example = "videoTitle")
	String title,

	@NotBlank(message = "비디오 설명은 필수 입력 항목입니다.")
	@Schema(description = "비디오 설명", example = "videoDescription")
	String description,

	@Schema(description = "비디오 태그", example = "videoTags")
	Set<VideoTag> videoTags
) {
}
