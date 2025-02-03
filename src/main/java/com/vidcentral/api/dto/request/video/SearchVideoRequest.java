package com.vidcentral.api.dto.request.video;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "비디오 검색 키워드 요청")
public record SearchVideoRequest(
	@NotBlank(message = "검색 키워드는 필수 입력 항목입니다.")
	@Schema(description = "검색 키워드", example = "keyword")
	String keyword
) {
}
