package com.vidcentral.api.dto.request.video;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UploadVideoRequest(
	@NotBlank(message = "비디오 제목은 필수 입력 항목입니다.")
	String title,

	String description
) {
}
